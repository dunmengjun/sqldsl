package com.dmj.sqldsl.builder.table;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.LambdaColumnBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.table.SimpleTable;
import com.dmj.sqldsl.model.table.Table;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EntityBuilder implements TableBuilder {

  private final Class<?> entityClass;

  private String alias;

  public static EntityBuilder alias(Class<?> entityClass) {
    String simpleName = entityClass.getSimpleName();
    return new EntityBuilder(entityClass, TableBuilder.getAlias(simpleName));
  }

  public EntityBuilder(Class<?> entityClass) {
    this.entityClass = entityClass;
  }

  public EntityBuilder(Class<?> entityClass, String alias) {
    this.entityClass = entityClass;
    this.alias = alias;
  }

  public <T, R> ColumnBuilder<T, R> col(ColumnLambda<T, R> lambda) {
    return new LambdaColumnBuilder<>(lambda.getLambdaType(), alias);
  }

  public Optional<String> getAlias() {
    return Optional.ofNullable(alias);
  }

  @Override
  public Table buildTable(EntityConfig config) {
    String tableName = config.getTableName(entityClass);
    if (alias == null) {
      return new SimpleTable(tableName);
    }
    return new SimpleTable(tableName, alias);
  }

  @Override
  public List<Column> buildColumns(EntityConfig config) {
    return config.getColumns(entityClass, alias);
  }
}
