package com.dmj.sqldsl.builder.column.type;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.SubQueryColumnBuilder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubQueryType implements ColumnBuilderFactory {

  private DslQueryBuilder queryBuilder;

  @Override
  public ColumnBuilder getColumnBuilder() {
    return new SubQueryColumnBuilder(queryBuilder);
  }
}
