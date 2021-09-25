package com.dmj.sqldsl.builder.column;

import static com.dmj.sqldsl.utils.EntityClassUtils.getTableName;
import static com.dmj.sqldsl.utils.ReflectionUtils.recursiveGetField;

import com.dmj.sqldsl.builder.column.type.LambdaType;
import com.dmj.sqldsl.builder.config.ColumnConfig;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.NoColumnAnnotationException;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.utils.exception.ReflectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(doNotUseGetters = true)
public class LambdaColumnBuilder<T, R> implements ColumnBuilder<T, R> {

  private final LambdaType lambdaType;

  private String alias;

  public LambdaColumnBuilder(LambdaType lambdaType) {
    this.lambdaType = lambdaType;
  }

  public LambdaColumnBuilder(LambdaType lambdaType, String alias) {
    this.lambdaType = lambdaType;
    this.alias = alias;
  }

  @Override
  public Column build(EntityConfig config) {
    String fieldName = config.getLambdaMethodTranslator().translate(lambdaType.getMethodName());
    return getColumn(lambdaType.getTargetClass(), fieldName, config);
  }

  private Column getColumn(Class<?> entityClass, String fieldName, EntityConfig config) {
    String tableName = Optional.ofNullable(alias)
        .orElse(getTableName(config.getTableConfig(), entityClass));
    ColumnConfig columnConfig = config.getColumnConfig();
    Class<? extends Annotation> columnClass = columnConfig.getColumnAnnotationClass();
    String columnName = columnConfig.getColumnName(getColumnField(entityClass, fieldName))
        .orElseThrow(() -> new NoColumnAnnotationException(entityClass, columnClass));
    return SimpleColumn.builder()
        .tableName(tableName)
        .fieldName(fieldName)
        .columnName(columnName)
        .build();
  }

  private Field getColumnField(Class<?> entityClass, String fieldName) {
    return recursiveGetField(entityClass, fieldName)
        .orElseThrow(() ->
            new ReflectionException(new NoSuchFieldException(
                String.format("No such field %s found in %s", fieldName, entityClass))));
  }
}
