package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.FunctionType;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.AliasColumn;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.FunctionColumn;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FunctionColumnBuilder implements ColumnBuilder {

  private FunctionType<?, ?> function;
  private ColumnLambda<?, ?> alias;

  @Override
  public Column build(EntityConfig config) {
    Column build = function.getColumnLambda().getColumnBuilder().build(config);
    String aliasName = config.getTranslator().translate(alias.getMethodName());
    return new FunctionColumn(function.getFunction(), new AliasColumn(build, aliasName));
  }
}
