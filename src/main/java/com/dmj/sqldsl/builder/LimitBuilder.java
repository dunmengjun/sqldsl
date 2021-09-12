package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LimitBuilder implements ToDslQuery {

  private FromBuilder fromBuilder;
  private int offset;
  private int size;

  @Override
  public DslQuery toQuery(EntityConfig config) {
    return DslQuery.builder()
        .selectFrom(fromBuilder.build(config))
        .limit(new Limit(offset, size))
        .build();
  }
}
