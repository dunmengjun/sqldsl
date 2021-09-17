package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LambdaAliasColumnBuilder implements ColumnBuilder {

  private ColumnLambda<?, ?> function;

  private ColumnLambda<?, ?> alias;

  @Override
  public Column build(EntityConfig config) {
    Column build = function.getColumnBuilder().build(config);
    String aliasName = config.getTranslator().translate(alias.getMethodName());
    return SimpleColumn.of(build, aliasName);
  }
}
