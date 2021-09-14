package com.dmj.sqldsl.builder.column;

import static com.dmj.sqldsl.utils.EntityClassUtils.getTableName;
import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.NoColumnAnnotationException;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.utils.StringUtils;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class EntityColumnsBuilder implements ColumnsBuilder {

  private final Class<?> entityClass;
  private final List<ColumnFunction<?, ?>> excludeColumnFunctions;

  public EntityColumnsBuilder(Class<?> entityClass,
      List<ColumnFunction<?, ?>> excludeColumnFunctions) {
    this.entityClass = entityClass;
    this.excludeColumnFunctions = excludeColumnFunctions;
  }

  @Override
  public List<Column> build(EntityConfig config) {
    Class<? extends Annotation> columnClass = config.getColumnAnnotation().getAnnotationClass();
    String finalTableName = getTableName(config.getTableAnnotation(), entityClass);
    String attribute = config.getColumnAnnotation().getColumnNameAttribute();
    List<Column> columns = Arrays.stream(entityClass.getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(columnClass))
        .map(field -> {
          String columnName = invokeMethod(attribute, field.getAnnotation(columnClass));
          if (StringUtils.isBlank(columnName)) {
            columnName = field.getName();
          }
          return SimpleColumn.builder()
              .tableName(finalTableName)
              .name(columnName)
              .build();
        })
        .collect(toList());
    if (columns.isEmpty()) {
      throw new NoColumnAnnotationException(entityClass, columnClass);
    }
    List<Column> excludeColumns = excludeColumnFunctions.stream()
        .map(columnFunction -> columnFunction.getColumnBuilder().build(config))
        .collect(toList());
    columns.removeAll(excludeColumns);
    return columns;
  }
}
