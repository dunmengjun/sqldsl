package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;

public class OrderByLimitBuilder extends LimitBuilder {

  private final OrderByBuilder orderByBuilder;

  public OrderByLimitBuilder(OrderByBuilder orderByBuilder, int offset, int size) {
    super(offset, size);
    this.orderByBuilder = orderByBuilder;
  }

  @Override
  protected DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    return orderByBuilder.build(config);
  }
}
