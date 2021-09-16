package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.FunctionColumnsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;

public class FromGroupByBuilder extends GroupByBuilder {

  private final FromBuilder fromBuilder;

  public FromGroupByBuilder(FromBuilder fromBuilder, FunctionColumnsBuilder columnsBuilder) {
    this.fromBuilder = fromBuilder;
    this.columnsBuilder = columnsBuilder;
  }

  @Override
  protected DslQuery.DslQueryBuilder build(EntityConfig config) {
    return fromBuilder.build(config).groupBy(this.buildGroupBy(config));
  }

  @Override
  public DslQuery toQuery(EntityConfig config) {
    return this.build(config).build();
  }
}
