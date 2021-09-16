package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;

public class GroupByLimitBuilder extends LimitBuilder {

  private final GroupByBuilder groupByBuilder;

  public GroupByLimitBuilder(GroupByBuilder groupByBuilder, int offset, int size) {
    super(offset, size);
    this.groupByBuilder = groupByBuilder;
  }

  @Override
  protected DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    return groupByBuilder.build(config);
  }
}
