package com.dmj.sqldsl.utils;

import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;

import com.dmj.sqldsl.builder.config.TableAnnotation;
import com.dmj.sqldsl.builder.exception.NoTableAnnotationException;
import java.lang.annotation.Annotation;

public class EntityClassUtils {

  public static String getTableName(TableAnnotation annotation, Class<?> entityClass) {
    Class<? extends Annotation> tableClass = annotation.getAnnotationClass();
    if (!entityClass.isAnnotationPresent(tableClass)) {
      throw new NoTableAnnotationException(entityClass, tableClass);
    }
    String tableNameAttribute = annotation.getTableNameAttribute();
    String tableName = invokeMethod(tableNameAttribute, entityClass.getAnnotation(tableClass));
    if (StringUtils.isBlank(tableName)) {
      tableName = entityClass.getSimpleName();
    }
    return tableName;
  }
}
