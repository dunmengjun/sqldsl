package com.dmj.sqldsl.driver;

import com.dmj.sqldsl.driver.exception.ExecutionException;
import com.dmj.sqldsl.driver.visitor.DslQueryVisitor;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.column.Column;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.dmj.sqldsl.utils.ReflectionUtils.newInstance;
import static com.dmj.sqldsl.utils.ReflectionUtils.setValue;

public class MysqlDriver implements Driver {

    private final Connection connection;

    @FunctionalInterface
    interface ResultSetFunction<T> {
        List<T> apply(ResultSet resultSet) throws SQLException;
    }

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
        String sql = new DslQueryVisitor().visit(query);
        System.out.println(sql);
        return executeQuery(connection, sql, resultSet -> {
            List<Column> columns = query.getSelect().getColumns();
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T target = newInstance(tClass);
                for (Column column : columns) {
                    String name = column.getName();
                    setValue(name, target, resultSet.getObject(name));
                }
                list.add(target);
            }
            return list;
        });
    }

    private <T> List<T> executeQuery(Connection connection,
                                     String sql,
                                     ResultSetFunction<T> function) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                return function.apply(resultSet);
            }
        }
    }
}
