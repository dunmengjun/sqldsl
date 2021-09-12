package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FunctionAliasColumnBuilder implements ColumnBuilder {

  private ColumnFunction<?, ?> function;

  private ColumnFunction<?, ?> alias;

  @Override
  public Column build(EntityConfig config) {
    ColumnBuilder columnBuilder = function.getColumnBuilder();
    Column build = columnBuilder.build(config);
    String aliasName = config.getTranslator().translate(alias.getMethodName());
    return new SimpleColumn(build.getTableName(), build.getName(), aliasName);
  }
}
