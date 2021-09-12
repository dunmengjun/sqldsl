package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import lombok.Getter;

@Getter
public class WhereBuilder implements ToDslQuery {

  private final FromBuilder fromBuilder;
  private final ConditionsBuilder conditionsBuilder;

  public WhereBuilder(FromBuilder fromBuilder, ConditionsBuilder conditionsBuilder) {
    this.fromBuilder = fromBuilder;
    this.conditionsBuilder = conditionsBuilder;
  }

  public WhereLimitBuilder limit(int offset, int size) {
    return new WhereLimitBuilder(this, offset, size);
  }

  public WhereLimitBuilder limit(int size) {
    return new WhereLimitBuilder(this, 0, size);
  }

  public DslQuery toQuery(EntityConfig config) {
    return DslQuery.builder()
        .selectFrom(fromBuilder.build(config))
        .conditions(conditionsBuilder.build(config))
        .build();
  }
}
