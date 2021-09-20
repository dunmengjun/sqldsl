package com.dmj.sqldsl.builder.table;

import static com.dmj.sqldsl.utils.EntityClassUtils.getColumnNames;
import static com.dmj.sqldsl.utils.EntityClassUtils.getTableName;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.LambdaColumnBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.NoColumnAnnotationException;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.model.table.SimpleTable;
import com.dmj.sqldsl.model.table.Table;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EntityTableBuilder implements TableBuilder {

  private final Class<?> entityClass;

  private String alias;

  public static EntityTableBuilder ref(Class<?> entityClass) {
    String simpleName = entityClass.getSimpleName();
    return new EntityTableBuilder(entityClass, TableBuilder.getAlias(simpleName));
  }

  public EntityTableBuilder(Class<?> entityClass) {
    this.entityClass = entityClass;
  }

  public EntityTableBuilder(Class<?> entityClass, String alias) {
    this.entityClass = entityClass;
    this.alias = alias;
  }

  public <T, R> ColumnBuilder<T, R> col(ColumnLambda<T, R> lambda) {
    return new LambdaColumnBuilder<>(lambda, alias);
  }

  public Optional<String> getAlias() {
    return Optional.ofNullable(alias);
  }

  @Override
  public Table build(EntityConfig config) {
    String tableName = getTableName(config.getTableAnnotation(), entityClass);
    String realName = getAlias().orElse(tableName);
    Class<? extends Annotation> columnClass = config.getColumnAnnotation().getAnnotationClass();
    List<Column> columns = getColumnNames(config.getColumnAnnotation(), entityClass)
        .map(columnName ->
            SimpleColumn.builder()
                .tableName(realName)
                .name(columnName)
                .build())
        .collect(toList());
    if (columns.isEmpty()) {
      throw new NoColumnAnnotationException(entityClass, columnClass);
    }
    return getAlias()
        .map(a -> new SimpleTable(columns, tableName, a))
        .orElse(new SimpleTable(columns, tableName));
  }
}
