package com.dmj.sqldsl.builder.column.type;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.SubQueryColumnBuilder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubQueryType<T, R> implements ColumnBuilderFactory<T, R> {

  private DslQueryBuilder queryBuilder;

  @Override
  public ColumnBuilder<T, R> getColumnBuilder() {
    return new SubQueryColumnBuilder<>(queryBuilder);
  }
}
