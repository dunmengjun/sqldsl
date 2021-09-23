package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;

public class WhereBuilder extends AbstractDslQueryBuilder {

  private final FromBuilder fromBuilder;
  private final ConditionsBuilder conditionsBuilder;

  public WhereBuilder(FromBuilder fromBuilder, ConditionsBuilder conditionsBuilder) {
    this.fromBuilder = fromBuilder;
    this.conditionsBuilder = conditionsBuilder;
  }

  @Override
  public DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    return fromBuilder.buildDslQueryBuilder(config).conditions(conditionsBuilder.build(config));
  }
}
