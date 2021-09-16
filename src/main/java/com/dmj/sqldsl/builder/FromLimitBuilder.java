package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;

public class FromLimitBuilder extends LimitBuilder {

  private final FromBuilder fromBuilder;

  public FromLimitBuilder(FromBuilder fromBuilder, int offset, int size) {
    super(offset, size);
    this.fromBuilder = fromBuilder;
  }

  @Override
  protected DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    return fromBuilder.build(config);
  }

  @Override
  public DslQuery toQuery(EntityConfig config) {
    return this.build(config).build();
  }
}
