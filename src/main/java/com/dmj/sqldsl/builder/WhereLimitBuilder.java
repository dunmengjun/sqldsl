package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;

public class WhereLimitBuilder extends LimitBuilder {

  private final WhereBuilder whereBuilder;

  public WhereLimitBuilder(WhereBuilder whereBuilder, int offset, int size) {
    super(offset, size);
    this.whereBuilder = whereBuilder;
  }

  @Override
  protected DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    return whereBuilder.build(config);
  }
}
