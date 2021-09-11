package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.table.TablesBuilder;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Select;
import com.dmj.sqldsl.model.condition.Conditions;

public class FromBuilder implements ToDslQuery {

  private final SelectBuilder selectBuilder;
  private final TablesBuilder tablesBuilder;

  public FromBuilder(SelectBuilder selectBuilder, TablesBuilder tablesBuilder) {
    this.selectBuilder = selectBuilder;
    this.tablesBuilder = tablesBuilder;
  }

  public WhereBuilder where(ConditionsBuilder conditionsBuilder) {
    return new WhereBuilder(this, conditionsBuilder);
  }

  protected Select build(EntityConfig config) {
    return Select.builder()
        .columns(selectBuilder.build(config))
        .tables(tablesBuilder.build(config))
        .build();
  }

  public DslQuery toQuery(EntityConfig config) {
    return DslQuery.builder()
        .select(this.build(config))
        .conditions(Conditions.empty())
        .build();
  }
}
