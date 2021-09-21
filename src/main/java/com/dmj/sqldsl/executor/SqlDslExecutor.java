package com.dmj.sqldsl.executor;

import static com.dmj.sqldsl.executor.visitor.ModelVisitorFactory.getModeVisitor;
import static com.dmj.sqldsl.utils.ReflectionUtils.newInstance;
import static com.dmj.sqldsl.utils.ReflectionUtils.recursiveSetValue;

import com.dmj.sqldsl.executor.exception.ExecutionException;
import com.dmj.sqldsl.executor.visitor.ModelVisitor;
import com.dmj.sqldsl.executor.visitor.Parameter;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.utils.AssertUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlDslExecutor implements Executor {

  private final Logger logger = LoggerFactory.getLogger(SqlDslExecutor.class);

  private final ConnectionManager manager;
  private final SqlDialect dialect;

  public SqlDslExecutor(SqlDialect dialect, ConnectionManager manager) {
    this.dialect = dialect;
    this.manager = manager;
  }

  @Override
  public <T> List<T> execute(DslQuery query, Class<T> targetClass) {
    if (targetClass.isPrimitive()) {
      throw new ExecutionException(
          "Jdbc not supported primitive type, cause your result type is: " + targetClass);
    }
    try {
      return executeQuery(query, targetClass);
    } catch (SQLException e) {
      throw new ExecutionException(e);
    }
  }

  private <T> List<T> executeQuery(DslQuery query, Class<T> targetClass) throws SQLException {
    ModelVisitor visitor = getModeVisitor(dialect);
    String sql = visitor.visit(query);
    logger.debug("=========================");
    logger.debug(sql);
    logger.debug(visitor.getParams().toString());
    logger.debug("=========================");
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
      if (!AssertUtils.isBaseClass(targetClass)) {
        return buildResult(targetClass, resultSet, query.getSelectFrom().getColumns());
      } else {
        return buildResult(targetClass, resultSet);
      }
    }
  }

  private <T> List<T> buildResult(Class<T> targetClass, ResultSet resultSet) throws SQLException {
    List<T> list = new ArrayList<>();
    if (resultSet.getMetaData().getColumnCount() != 1) {
      logger.warn(
          String.format("The result set actual has %s column of data, "
                  + "With given result type %s, will return only the first column data",
              resultSet.getRow(), targetClass));
    }
    while (resultSet.next()) {
      list.add(resultSet.getObject(1, targetClass));
    }
    return list;
  }

  private <T> List<T> buildResult(Class<T> targetClass, ResultSet resultSet, List<Column> columns)
      throws SQLException {
    List<T> list = new ArrayList<>();
    while (resultSet.next()) {
      T target = newInstance(targetClass);
      for (Column column : columns) {
        String name = column.getRealName();
        recursiveSetValue(name, target, resultSet.getObject(name));
      }
      list.add(target);
    }
    return list;
  }

  private void setParameter(PreparedStatement statement, List<Parameter> params)
      throws SQLException {
    for (int i = 0; i < params.size(); i++) {
      statement.setObject(i + 1, params.get(i).getValue());
    }
  }
}
