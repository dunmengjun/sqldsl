package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;

public class WhereBuilder implements ToDslQuery {

  private final FromBuilder fromBuilder;
  private final ConditionsBuilder conditionsBuilder;

  public WhereBuilder(FromBuilder fromBuilder, ConditionsBuilder conditionsBuilder) {
    this.fromBuilder = fromBuilder;
    this.conditionsBuilder = conditionsBuilder;
  }

  public DslQuery toQuery(EntityConfig config) {
    return DslQuery.builder()
        .selectFrom(fromBuilder.build(config))
        .conditions(conditionsBuilder.build(config))
        .build();
  }
}
