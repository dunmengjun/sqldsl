package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import java.util.List;

public class FromOrderByBuilder extends OrderByBuilder {

  private final FromBuilder fromBuilder;

  public FromOrderByBuilder(FromBuilder fromBuilder, List<OrderBuilder> orderBuilders) {
    super(orderBuilders);
    this.fromBuilder = fromBuilder;
  }

  @Override
  protected DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    return fromBuilder.build(config);
  }
}
