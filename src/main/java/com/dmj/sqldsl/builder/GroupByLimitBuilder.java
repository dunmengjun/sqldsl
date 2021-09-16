package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GroupByLimitBuilder implements LimitBuilder {

  private GroupByBuilder groupByBuilder;
  private int offset;
  private int size;

  protected DslQuery.DslQueryBuilder build(EntityConfig config) {
    return groupByBuilder.build(config).limit(new Limit(offset, size));
  }

  @Override
  public DslQuery toQuery(EntityConfig config) {
    return this.build(config).build();
  }
}
