package com.dmj.sqldsl.executor;

import static com.dmj.sqldsl.executor.visitor.ModelVisitorFactory.getModeVisitor;
import static com.dmj.sqldsl.utils.ReflectionUtils.newInstance;
import static com.dmj.sqldsl.utils.ReflectionUtils.recursiveSetValue;
import static java.util.stream.Collectors.toMap;

import com.dmj.sqldsl.executor.exception.ExecutionException;
import com.dmj.sqldsl.executor.visitor.ModelVisitor;
import com.dmj.sqldsl.executor.visitor.Parameter;
import com.dmj.sqldsl.executor.visitor.TableEntityVisitor;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Entity;
import com.dmj.sqldsl.model.ModifiedFlag;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.utils.AssertUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SqlDslExecutor implements Executor {

  private final ConnectionManager manager;
  private final SqlDialect dialect;

  public SqlDslExecutor(SqlDialect dialect, ConnectionManager manager) {
    this.dialect = dialect;
    this.manager = manager;
  }

  @Override
  public <T> List<T> execute(DslQuery query, Class<T> resultClass) {
    if (resultClass.isPrimitive()) {
      throw new ExecutionException(
          "Jdbc not supported primitive type, cause your result type is: " + resultClass);
    }
    try {
      return executeQuery(query, resultClass);
    } catch (SQLException e) {
      throw new ExecutionException(e);
    }
  }

  @Override
  public int save(Entity entity) {
    try {
      return saveOne(entity);
    } catch (SQLException e) {
      throw new ExecutionException(e);
    }
  }

  @Override
  public BatchResult save(List<Entity> entities) {
    return saveList(entities);
  }

  private int saveOne(Entity entity) throws SQLException {
    TableEntityVisitor visitor = new TableEntityVisitor();
    String sql = visitor.visit(entity);
    logSql(sql, visitor.getParams());
    try (Connection connection = manager.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        setParameter(statement, visitor.getParams());
        int i = statement.executeUpdate();
        log.debug("<== {}", i);
        log.debug("=========================");
        return i;
      }
    }
  }

  @AllArgsConstructor
  @Getter
  static class IndexEntity {

    private Entity entity;
    private int index;
  }

  private BatchResult saveList(List<Entity> entities) {
    List<IndexEntity> indexEntities = new ArrayList<>(entities.size());
    for (int i = 0; i < entities.size(); i++) {
      indexEntities.add(new IndexEntity(entities.get(i), i));
    }
    Map<Integer, List<IndexEntity>> entityListMap = indexEntities.stream()
        .collect(toListMap(indexEntity -> indexEntity.getEntity().hashCode()));
    return entityListMap.values().stream().map(entityList -> {
          List<IndexEntity> insertList = new ArrayList<>(entityList.size());
          List<IndexEntity> updateList = new ArrayList<>(entityList.size());
          for (IndexEntity entity : entityList) {
            ModifiedFlag modifiedFlag = entity.getEntity().getModifiedFlag();
            if (ModifiedFlag.UPDATE == modifiedFlag) {
              updateList.add(entity);
            } else if (ModifiedFlag.INSERT == modifiedFlag) {
              insertList.add(entity);
            }
          }
          return tryExecuteBatch(insertList).add(tryExecuteBatch(updateList));
        })
        .reduce(BatchResult::add)
        .orElseThrow(() -> new ExecutionException("no batch result return!"));
  }

  private <T, K> Collector<T, ?, Map<K, List<T>>> toListMap(Function<T, K> function) {
    return toMap(function, l -> {
      List<T> list = new ArrayList<>();
      list.add(l);
      return list;
    }, (oldList, newList) -> {
      oldList.addAll(newList);
      return oldList;
    });
  }

  private BatchResult tryExecuteBatch(List<IndexEntity> indexEntities) {
    try {
      return executeBatch(indexEntities);
    } catch (SQLException e) {
      throw new ExecutionException(e);
    }
  }

  private BatchResult executeBatch(List<IndexEntity> indexEntities) throws SQLException {
    if (indexEntities.isEmpty()) {
      return BatchResult.empty();
    }
    Entity entity = indexEntities.get(0).getEntity();
    TableEntityVisitor visitor = new TableEntityVisitor();
    String sql = visitor.visit(entity);
    log.debug("=========================");
    log.debug("==> {}", sql);
    try (Connection connection = manager.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        List<Integer> indexList = new ArrayList<>(indexEntities.size());
        for (IndexEntity e : indexEntities) {
          indexList.add(e.getIndex());
          TableEntityVisitor v = new TableEntityVisitor();
          v.visit(e.getEntity());
          log.debug("==> {}", v.getParams().toString());
          setParameter(statement, v.getParams());
          statement.addBatch();
        }
        int[] array = statement.executeBatch();
        BatchResult saveResult = BatchResult.create(indexList, array);
        log.debug("<== {}", saveResult.getAffectedRows());
        log.debug("=========================");
        return saveResult;
      }
    }
  }

  private void logSql(String sql, List<Parameter> parameters) {
    log.debug("=========================");
    log.debug("==> {}", sql);
    log.debug("==> {}", parameters.toString());
  }

  private <T> List<T> executeQuery(DslQuery query, Class<T> targetClass) throws SQLException {
    ModelVisitor visitor = getModeVisitor(dialect);
    String sql = visitor.visit(query);
    logSql(sql, visitor.getParams());
    try (Connection connection = manager.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        setParameter(statement, visitor.getParams());
        List<T> resultList = executeQuery(statement, query, targetClass);
        log.debug("<== {}", resultList.size());
        log.debug("=========================");
        return resultList;
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

  private <T> List<T> buildResult(Class<T> resultClass, ResultSet resultSet) throws SQLException {
    int columnCount = resultSet.getMetaData().getColumnCount();
    if (columnCount > 1) {
      log.warn(String.format("The result set actual has %s column of data, "
              + "With given result type %s, will return only the first column data",
          columnCount, resultClass));
    }
    List<T> list = new ArrayList<>();
    while (resultSet.next()) {
      list.add(resultSet.getObject(1, resultClass));
    }
    return list;
  }

  private <T> List<T> buildResult(Class<T> targetClass, ResultSet resultSet, List<Column> columns)
      throws SQLException {
    List<T> list = new ArrayList<>();
    while (resultSet.next()) {
      T target = newInstance(targetClass);
      for (Column column : columns) {
        String fieldName = column.getFieldName();
        String columnName = column.getColumnName();
        recursiveSetValue(fieldName, target, resultSet.getObject(columnName));
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
