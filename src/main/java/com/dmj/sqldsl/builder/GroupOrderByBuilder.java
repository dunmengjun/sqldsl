package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import java.util.List;

public class GroupOrderByBuilder extends OrderByBuilder {

  private final GroupByBuilder groupByBuilder;

  public GroupOrderByBuilder(GroupByBuilder groupByBuilder, List<OrderBuilder> orderBuilders) {
    super(orderBuilders);
    this.groupByBuilder = groupByBuilder;
  }

  @Override
  protected DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    return groupByBuilder.build(config);
  }
}
