package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.column.type.LambdaType;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.AliasColumn;
import com.dmj.sqldsl.model.column.Column;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AliasColumnBuilder<T, R> implements ColumnBuilder<T, R> {

  private ColumnBuilder<?, ?> columnBuilder;

  private LambdaType alias;

  @Override
  public Column build(EntityConfig config) {
    return new AliasColumn(columnBuilder.build(config), config.translateLambda(alias));
  }
}
