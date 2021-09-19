package com.dmj.sqldsl.builder.column;

import static com.dmj.sqldsl.utils.EntityClassUtils.getAlias;
import static com.dmj.sqldsl.utils.EntityClassUtils.getColumnNames;
import static com.dmj.sqldsl.utils.EntityClassUtils.getTableName;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.NoColumnAnnotationException;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import java.lang.annotation.Annotation;
import java.util.List;

public class EntityColumnsBuilder implements ColumnsBuilder {

  private final Class<?> entityClass;
  private final List<ColumnLambda<?, ?>> excludeColumnLambdas;

  public EntityColumnsBuilder(Class<?> entityClass,
      List<ColumnLambda<?, ?>> excludeColumnLambdas) {
    this.entityClass = entityClass;
    this.excludeColumnLambdas = excludeColumnLambdas;
  }

  @Override
  public List<Column> build(EntityConfig config) {
    String finalTableName = getAlias(entityClass)
        .orElse(getTableName(config.getTableAnnotation(), entityClass));
    Class<? extends Annotation> columnClass = config.getColumnAnnotation().getAnnotationClass();
    List<Column> columns = getColumnNames(config.getColumnAnnotation(), entityClass)
        .map(columnName ->
            SimpleColumn.builder()
                .tableName(finalTableName)
                .name(columnName)
                .build())
        .collect(toList());
    if (columns.isEmpty()) {
      throw new NoColumnAnnotationException(entityClass, columnClass);
    }
    List<Column> excludeColumns = excludeColumnLambdas.stream()
        .map(columnFunction -> columnFunction.getColumnBuilder().build(config))
        .collect(toList());
    columns.removeAll(excludeColumns);
    return columns;
  }
}
