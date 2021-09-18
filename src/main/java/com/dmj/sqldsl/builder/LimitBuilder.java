package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Limit;

public abstract class LimitBuilder implements DslQueryBuilder {

  private final int offset;
  private final int size;

  public LimitBuilder(int offset, int size) {
    this.offset = offset;
    this.size = size;
  }

  protected abstract DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config);

  protected DslQuery.DslQueryBuilder build(EntityConfig config) {
    return buildDslQueryBuilder(config).limit(new Limit(offset, size));
  }

  @Override
  public DslQuery toQuery(EntityConfig config) {
    return this.build(config).build();
  }
}
