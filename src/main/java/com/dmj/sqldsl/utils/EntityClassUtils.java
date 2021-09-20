package com.dmj.sqldsl.utils;

import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;
import static com.dmj.sqldsl.utils.ReflectionUtils.recursiveGetFields;

import com.dmj.sqldsl.builder.config.ColumnAnnotation;
import com.dmj.sqldsl.builder.config.TableAnnotation;
import com.dmj.sqldsl.builder.exception.NoTableAnnotationException;
import java.lang.annotation.Annotation;
import java.util.stream.Stream;

public class EntityClassUtils {

  public static String getTableName(TableAnnotation tableConfig, Class<?> entityClass) {
    Class<? extends Annotation> annotationClass = tableConfig.getAnnotationClass();
    if (entityClass.isAnnotationPresent(annotationClass)) {
      Annotation annotation = entityClass.getAnnotation(annotationClass);
      String tableName = invokeMethod(tableConfig.getTableNameAttribute(), annotation);
      if (StringUtils.isBlank(tableName)) {
        tableName = entityClass.getSimpleName();
      }
      return tableName;
    }
    Class<?> superclass = entityClass.getSuperclass();
    if (superclass == null) {
      throw new NoTableAnnotationException(entityClass, annotationClass);
    }
    return getTableName(tableConfig, superclass);
  }

  public static Stream<String> getColumnNames(ColumnAnnotation columnConfig, Class<?> entityClass) {
    Class<? extends Annotation> annotationClass = columnConfig.getAnnotationClass();
    String attribute = columnConfig.getColumnNameAttribute();
    return recursiveGetFields(entityClass, annotationClass)
        .map(field -> {
          Annotation annotation = field.getAnnotation(annotationClass);
          String columnName = invokeMethod(attribute, annotation);
          if (StringUtils.isBlank(columnName)) {
            columnName = field.getName();
          }
          return columnName;
        });
  }
}
