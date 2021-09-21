package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;

public class AllColumnBuilder<T, R> implements ColumnBuilder<T, R> {

  @Override
  public Column build(EntityConfig config) {
    return SimpleColumn.builder()
        .name("*")
        .build();
  }
}
