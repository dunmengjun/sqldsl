package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.condition.ConditionalExpression;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import lombok.Getter;

@Getter
public class WhereBuilder implements ToDslQuery {

  private final FromBuilder fromBuilder;
  private final ConditionalExpression conditionalExpression;

  public WhereBuilder(FromBuilder fromBuilder, ConditionalExpression conditionalExpression) {
    this.fromBuilder = fromBuilder;
    this.conditionalExpression = conditionalExpression;
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
        .conditions(conditionalExpression.build(config))
        .build();
  }
}
