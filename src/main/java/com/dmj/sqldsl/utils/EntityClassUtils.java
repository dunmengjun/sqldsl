package com.dmj.sqldsl.utils;

import static com.dmj.sqldsl.utils.ReflectionUtils.recursiveGetFields;

import com.dmj.sqldsl.builder.config.ColumnConfig;
import com.dmj.sqldsl.builder.config.TableConfig;
import com.dmj.sqldsl.builder.exception.NoTableAnnotationException;
import java.util.Optional;
import java.util.stream.Stream;

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

  public static Stream<String> getColumnNames(ColumnConfig columnConfig, Class<?> entityClass) {
    return recursiveGetFields(entityClass)
        .map(columnConfig::getColumnName)
        .filter(Optional::isPresent)
        .map(Optional::get);
  }
}
