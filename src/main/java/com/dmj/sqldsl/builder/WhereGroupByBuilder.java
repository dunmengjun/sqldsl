package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;

public class WhereGroupByBuilder extends GroupByBuilder {

  private final WhereBuilder whereBuilder;

  public WhereGroupByBuilder(WhereBuilder whereBuilder,
      NormalColumnsBuilder columnsBuilder) {
    this.whereBuilder = whereBuilder;
    this.columnsBuilder = columnsBuilder;
  }

  @Override
  protected DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    return whereBuilder.build(config);
  }

  @Override
  public void setSelectBuilder(SelectBuilder selectBuilder) {
    whereBuilder.setSelectBuilder(selectBuilder);
  }

  @Override
  public SelectBuilder getSelectBuilder() {
    return whereBuilder.getSelectBuilder();
  }
}
