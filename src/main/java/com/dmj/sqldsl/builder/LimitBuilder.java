package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Limit;

public class LimitBuilder extends AbstractDslQueryBuilder {

  private final AbstractDslQueryBuilder queryBuilder;
  private final int offset;
  private final int size;

  public LimitBuilder(AbstractDslQueryBuilder queryBuilder, int offset, int size) {
    this.queryBuilder = queryBuilder;
    this.offset = offset;
    this.size = size;
  }

  @Override
  public DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    return queryBuilder.buildDslQueryBuilder(config).limit(new Limit(offset, size));
  }

  @Override
  public void setSelectBuilder(SelectBuilder selectBuilder) {
    this.queryBuilder.setSelectBuilder(selectBuilder);
  }

  @Override
  public SelectBuilder getSelectBuilder() {
    return this.queryBuilder.getSelectBuilder();
  }
}
