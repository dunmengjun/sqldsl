package com.dmj.sqldsl.utils;

import static com.dmj.sqldsl.utils.ReflectionUtils.recursiveGetFields;

import com.dmj.sqldsl.builder.config.ColumnConfig;
import com.dmj.sqldsl.builder.config.TableConfig;
import com.dmj.sqldsl.builder.exception.NoTableAnnotationException;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EntityClassUtils {

  public static String getTableName(TableConfig tableConfig, Class<?> entityClass) {
    return tableConfig.getTableName(entityClass)
        .orElseGet(() -> {
          Class<?> superclass = entityClass.getSuperclass();
          if (superclass == null) {
            throw new NoTableAnnotationException(entityClass, tableConfig.getAnnotationClass());
          }
          return getTableName(tableConfig, superclass);
        });
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class FieldColumn {

    private String fieldName;
    private String columnName;
  }

  public static Stream<FieldColumn> getColumnNames(ColumnConfig columnConfig,
      Class<?> entityClass) {
    return recursiveGetFields(entityClass)
        .map(field -> columnConfig.getColumnName(field)
            .map(name -> new FieldColumn(field.getName(), name)))
        .filter(Optional::isPresent)
        .map(Optional::get);
  }
}
