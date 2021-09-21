package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SubQueryValueColumn;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubQueryColumnBuilder<T, R> implements ColumnBuilder<T, R> {

  private DslQueryBuilder queryBuilder;

  @Override
  public Column build(EntityConfig config) {
    return new SubQueryValueColumn(queryBuilder.build(config).build());
  }
}
