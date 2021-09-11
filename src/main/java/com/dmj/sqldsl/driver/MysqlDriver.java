package com.dmj.sqldsl.driver;

import com.dmj.sqldsl.driver.exception.ExecutionException;
import com.dmj.sqldsl.driver.visitor.DslQueryVisitor;
import com.dmj.sqldsl.driver.visitor.Parameter;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.column.Column;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.dmj.sqldsl.utils.ReflectionUtils.*;

public class MysqlDriver implements Driver {

    private final ConnectionManager manager;

    public MysqlDriver(ConnectionManager manager) {
        this.manager = manager;
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
        DslQueryVisitor visitor = new DslQueryVisitor();
        String sql = visitor.visit(query);
        System.out.println(sql);
        try (Connection connection = manager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setParameter(statement, visitor.getParams());
                return executeQuery(statement, query, tClass);
            }
        }
    }

    private void setParameter(PreparedStatement statement, List<Parameter> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i).getValue());
        }
    }

    private <T> List<T> executeQuery(PreparedStatement statement,
                                     DslQuery query,
                                     Class<T> tClass) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            List<Column> columns = query.getSelect().getColumns();
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T target = newInstance(tClass);
                for (Column column : columns) {
                    String name = column.getName();
                    if (hasField(tClass, name)) {
                        setValue(name, target, resultSet.getObject(name));
                    }
                }
                list.add(target);
            }
            return list;
        }
    }
}
