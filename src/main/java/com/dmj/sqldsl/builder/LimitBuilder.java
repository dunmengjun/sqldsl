package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Limit;

public class LimitBuilder implements DslQueryBuilder {

  private final DslQueryBuilder queryBuilder;
  private final int offset;
  private final int size;

  public LimitBuilder(DslQueryBuilder queryBuilder, int offset, int size) {
    this.queryBuilder = queryBuilder;
    this.offset = offset;
    this.size = size;
  }

  @Override
  public DslQuery.DslQueryBuilder build(EntityConfig config) {
    return queryBuilder.build(config).limit(new Limit(offset, size));
  }
}
