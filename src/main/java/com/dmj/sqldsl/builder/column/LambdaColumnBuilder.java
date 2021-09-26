package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.column.type.LambdaType;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.NoColumnAnnotationException;
import com.dmj.sqldsl.model.column.Column;
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
    return config.getColumn(lambdaType, alias)
        .orElseThrow(() -> new NoColumnAnnotationException(
            lambdaType.getTargetClass(), config.getColumnConfig().getColumnAnnotationClass()));
  }
}
