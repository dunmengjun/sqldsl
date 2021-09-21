package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.AliasColumn;
import com.dmj.sqldsl.model.column.Column;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AliasColumnBuilder<T, R> implements ColumnBuilder<T, R> {

  private ColumnBuilder<?, ?> columnBuilder;

  private ColumnLambda<?, ?> alias;

  @Override
  public Column build(EntityConfig config) {
    Column build = columnBuilder.build(config);
    String aliasName = config.getTranslator().translate(alias.getMethodName());
    return new AliasColumn(build, aliasName);
  }
}
