package com.dmj.sqldsl.builder.column.type;

import com.dmj.sqldsl.builder.column.ColumnBuilder;

public interface ColumnBuilderFactory {

  ColumnBuilder getColumnBuilder();
}
