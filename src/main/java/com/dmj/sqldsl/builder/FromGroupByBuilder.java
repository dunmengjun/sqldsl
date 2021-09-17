package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;

public class FromGroupByBuilder extends GroupByBuilder {

  private final FromBuilder fromBuilder;

  public FromGroupByBuilder(FromBuilder fromBuilder, NormalColumnsBuilder columnsBuilder) {
    this.fromBuilder = fromBuilder;
    this.columnsBuilder = columnsBuilder;
  }

  @Override
  protected DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    return fromBuilder.build(config);
  }
}
