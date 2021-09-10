package com.dmj.sqldsl.driver;

import com.dmj.sqldsl.driver.exception.ExecutionException;
import com.dmj.sqldsl.driver.exception.NotSupportedColumnException;
import com.dmj.sqldsl.driver.exception.NotSupportedTableException;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Select;
import com.dmj.sqldsl.model.SimpleTable;
import com.dmj.sqldsl.model.Table;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.model.condition.*;
import com.dmj.sqldsl.utils.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.dmj.sqldsl.utils.ReflectionUtils.newInstance;
import static com.dmj.sqldsl.utils.ReflectionUtils.setValue;
import static java.util.stream.Collectors.joining;

public class MysqlDriver implements Driver {

    private final Connection connection;

    public MysqlDriver(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> List<T> execute(DslQuery query, Class<T> tClass) {
        try {
            return executeQuery(query, tClass);
        } catch (SQLException e) {
            throw new ExecutionException(e);
        }
    }

    private <T> List<T> executeQuery(DslQuery query, Class<T> tClass) throws SQLException {
        return executeQuery(connection, query, resultSet -> {
            ResultSetMetaData metaData = resultSet.getMetaData();
            List<Column> columns = query.getSelect().getColumns();
            int columnsNumber = metaData.getColumnCount();
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T target = newInstance(tClass);
                for (int i = 1; i <= columnsNumber; i++) {
                    setValue(columns.get(i - 1).getName(), target, resultSet.getObject(i));
                }
                list.add(target);
            }
            return list;
        });
    }

    @FunctionalInterface
    interface ResultSetFunction<T> {
        List<T> apply(ResultSet resultSet) throws SQLException;
    }

    private <T> List<T> executeQuery(Connection connection,
                                     DslQuery query,
                                     ResultSetFunction<T> function) throws SQLException {
        String sql = toSqlString(query);
        System.out.println(sql);
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                return function.apply(resultSet);
            }
        }
    }

    public String toSqlString(DslQuery query) {
        String selectSqlString = toSqlString(query.getSelect());
        String conditionsSqlString = toSqlString(query.getConditions());
        return String.format("%s %s", selectSqlString, conditionsSqlString);
    }

    private String toSqlString(Select select) {
        List<Column> columns = select.getColumns();
        String columnsSqlString = columns.stream().map(x -> {
            if (x instanceof SimpleColumn) {
                SimpleColumn simpleColumn = (SimpleColumn) x;
                return String.format("%s.%s", simpleColumn.getTableName(), simpleColumn.getName());
            }
            throw new NotSupportedColumnException(x);
        }).collect(joining(", "));

        List<Table> tables = select.getTables();
        String tablesSqlString = tables.stream().map(x -> {
            if (x instanceof SimpleTable) {
                SimpleTable simpleTable = (SimpleTable) x;
                return simpleTable.getTableName();
            }
            throw new NotSupportedTableException(x);
        }).collect(joining(","));

        return String.format("select %s from %s", columnsSqlString, tablesSqlString);
    }

    private String toSqlString(Conditions conditions) {
        String conditionsSqlString = conditions.getConditionElements().stream()
                .map(this::toSqlString)
                .collect(joining(" "));
        if (StringUtils.isBlank(conditionsSqlString)) {
            return "";
        }
        return "where " + conditionsSqlString;
    }

    private String toSqlString(ConditionElement element) {
        if (element instanceof Conditions) {
            Conditions conditions = (Conditions) element;
            List<ConditionElement> conditionElements = conditions.getConditionElements();
            return conditionElements.stream()
                    .map(this::toSqlString)
                    .collect(joining(" ", "(", ")"));
        } else if (element instanceof ColumnValueCondition) {
            ColumnValueCondition valueCondition = (ColumnValueCondition) element;
            Column column = valueCondition.getColumn();
            Object value = valueCondition.getValue();
            return String.format("%s = %s", toSqlString(column), valueSqlString(value));
        } else if (element instanceof Or) {
            return "or";
        } else if (element instanceof And) {
            return "and";
        }
        return "";
    }

    private String valueSqlString(Object value) {
        if (value instanceof String) {
            return String.format("'%s'", value);
        }
        return value.toString();
    }

    private String toSqlString(Column column) {
        if (column instanceof SimpleColumn) {
            SimpleColumn simpleColumn = (SimpleColumn) column;
            return String.format("%s.%s", simpleColumn.getTableName(), simpleColumn.getName());
        }
        return "";
    }
}
