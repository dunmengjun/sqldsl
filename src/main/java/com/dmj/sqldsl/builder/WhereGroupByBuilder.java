package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.FunctionColumnsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;

public class WhereGroupByBuilder extends GroupByBuilder {

  private final WhereBuilder whereBuilder;

  public WhereGroupByBuilder(WhereBuilder whereBuilder,
      FunctionColumnsBuilder columnsBuilder) {
    this.whereBuilder = whereBuilder;
    this.columnsBuilder = columnsBuilder;
  }

  @Override
  protected DslQuery.DslQueryBuilder build(EntityConfig config) {
    return whereBuilder.build(config).groupBy(this.buildGroupBy(config));
  }

  @Override
  public DslQuery toQuery(EntityConfig config) {
    return this.build(config).build();
  }
}
