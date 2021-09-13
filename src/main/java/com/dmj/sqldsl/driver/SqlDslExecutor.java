package com.dmj.sqldsl.driver;

import static com.dmj.sqldsl.utils.ReflectionUtils.hasField;
import static com.dmj.sqldsl.utils.ReflectionUtils.newInstance;
import static com.dmj.sqldsl.utils.ReflectionUtils.setValue;

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

public class SqlDslExecutor implements Executor {

  private final ConnectionManager manager;
  private final SqlDialect dialect;

  public SqlDslExecutor(SqlDialect dialect, ConnectionManager manager) {
    this.dialect = dialect;
    this.manager = manager;
  }

  @Override
  public <T> List<T> execute(DslQuery query, Class<T> targetClass) {
    try {
      return executeQuery(query, targetClass);
    } catch (SQLException e) {
      throw new ExecutionException(e);
    }
  }

  private <T> List<T> executeQuery(DslQuery query, Class<T> targetClass) throws SQLException {
    DslQueryVisitor visitor = new DslQueryVisitor();
    String sql = visitor.visit(query);
    System.out.println(sql);
    try (Connection connection = manager.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        setParameter(statement, visitor.getParams());
        return executeQuery(statement, query, targetClass);
      }
    }
  }

  private <T> List<T> executeQuery(PreparedStatement statement,
      DslQuery query,
      Class<T> targetClass) throws SQLException {
    try (ResultSet resultSet = statement.executeQuery()) {
      List<Column> columns = query.getSelectFrom().getColumns();
      List<T> list = new ArrayList<>();
      while (resultSet.next()) {
        T target = newInstance(targetClass);
        for (Column column : columns) {
          String name = column.getAlias().orElse(column.getName());
          if (hasField(targetClass, name)) {
            setValue(name, target, resultSet.getObject(name));
          }
        }
        list.add(target);
      }
      return list;
    }
  }

  private void setParameter(PreparedStatement statement, List<Parameter> params)
      throws SQLException {
    for (int i = 0; i < params.size(); i++) {
      statement.setObject(i + 1, params.get(i).getValue());
    }
  }
}
