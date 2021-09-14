package com.dmj.sqldsl.builder.column;

import static com.dmj.sqldsl.utils.EntityClassUtils.getTableName;
import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.NoColumnAnnotationException;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.utils.ReflectionUtils;
import com.dmj.sqldsl.utils.StringUtils;
import com.dmj.sqldsl.utils.exception.ReflectionException;
import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;

public class LambdaColumnBuilder implements ColumnBuilder {

  private final SerializedLambda lambda;

  public LambdaColumnBuilder(SerializedLambda lambda) {
    this.lambda = lambda;
  }

  @Override
  public Column build(EntityConfig config) {
    String implClass = lambda.getImplClass().replace("/", ".");
    String implMethodName = lambda.getImplMethodName();
    String fieldName = config.getTranslator().translate(implMethodName);
    try {
      Class<?> entityClass = Class.forName(implClass);
      return getColumn(entityClass, fieldName, config);
    } catch (ClassNotFoundException e) {
      throw new ReflectionException(e);
    }
  }

  private Column getColumn(Class<?> entityClass, String fieldName, EntityConfig config) {
    String tableName = getTableName(config.getTableAnnotation(), entityClass);
    Field field = ReflectionUtils.getField(entityClass, fieldName);
    Class<? extends Annotation> columnClass = config.getColumnAnnotation().getAnnotationClass();
    if (!field.isAnnotationPresent(columnClass)) {
      throw new NoColumnAnnotationException(entityClass, columnClass);
    }
    String attribute = config.getColumnAnnotation().getColumnNameAttribute();
    String columnName = invokeMethod(attribute, field.getAnnotation(columnClass));
    if (StringUtils.isBlank(columnName)) {
      columnName = field.getName();
    }
    return SimpleColumn.builder()
        .tableName(tableName)
        .name(columnName)
        .build();
  }
}
