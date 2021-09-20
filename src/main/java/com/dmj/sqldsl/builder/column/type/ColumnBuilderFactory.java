package com.dmj.sqldsl.builder.column.type;

import com.dmj.sqldsl.builder.column.ColumnBuilder;

public interface ColumnBuilderFactory<T, R> {

  ColumnBuilder<T, R> getColumnBuilder();
}
