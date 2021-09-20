package com.dmj.sqldsl.builder.column;

import static com.dmj.sqldsl.utils.EntityClassUtils.getTableName;
import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;
import static com.dmj.sqldsl.utils.ReflectionUtils.recursiveGetField;

import com.dmj.sqldsl.builder.column.type.SerializableLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.NoColumnAnnotationException;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.utils.StringUtils;
import com.dmj.sqldsl.utils.exception.ReflectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

public class LambdaColumnBuilder<T, R> implements ColumnBuilder<T, R> {

  private final SerializableLambda<T, R> lambda;

  private String alias;

  public LambdaColumnBuilder(SerializableLambda<T, R> lambda) {
    this.lambda = lambda;
  }

  public LambdaColumnBuilder(SerializableLambda<T, R> lambda, String alias) {
    this.lambda = lambda;
    this.alias = alias;
  }

  @Override
  public Column build(EntityConfig config) {
    String fieldName = config.getTranslator().translate(lambda.getMethodName());
    return getColumn(lambda.getTargetClass(), fieldName, config);
  }

  private Column getColumn(Class<?> entityClass, String fieldName, EntityConfig config) {
    String tableName = Optional.ofNullable(alias)
        .orElse(getTableName(config.getTableAnnotation(), entityClass));
    Field field = getColumnField(entityClass, fieldName);
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

  private Field getColumnField(Class<?> entityClass, String fieldName) {
    return recursiveGetField(entityClass, fieldName)
        .orElseThrow(() ->
            new ReflectionException(new NoSuchFieldException(
                String.format("No such field %s found in %s", fieldName, entityClass))));
  }
}
