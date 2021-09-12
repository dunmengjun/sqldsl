package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WhereLimitBuilder implements LimitBuilder {

  private WhereBuilder whereBuilder;
  private int offset;
  private int size;

  @Override
  public DslQuery toQuery(EntityConfig config) {
    return DslQuery.builder()
        .selectFrom(whereBuilder.getFromBuilder().build(config))
        .conditions(whereBuilder.getConditionsBuilder().build(config))
        .limit(new Limit(offset, size))
        .build();
  }
}
