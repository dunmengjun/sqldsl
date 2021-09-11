package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FunctionAliasColumnsBuilder implements ColumnsBuilder {

  private ColumnFunction<?, ?> function;

  private ColumnFunction<?, ?> alias;

  @Override
  public List<Column> build(EntityConfig config) {
    return null;
  }
}
