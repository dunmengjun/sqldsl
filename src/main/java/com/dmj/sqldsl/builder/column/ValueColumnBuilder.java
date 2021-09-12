package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.ValueColumn;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValueColumnBuilder implements ColumnBuilder {

  private Object value;

  @Override
  public Column build(EntityConfig config) {
    return new ValueColumn(value);
  }
}
