package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.GroupBy;
import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import java.util.Optional;

public class GroupByBuilder extends AbstractDslQueryBuilder {

  protected AbstractDslQueryBuilder queryBuilder;
  protected ConditionsBuilder havingConditions;
  protected NormalColumnsBuilder columnsBuilder;

  public GroupByBuilder(AbstractDslQueryBuilder queryBuilder, NormalColumnsBuilder columnsBuilder) {
    this.queryBuilder = queryBuilder;
    this.columnsBuilder = columnsBuilder;
  }

  public final GroupByBuilder having(ConditionsBuilder havingConditions) {
    this.havingConditions = havingConditions;
    return this;
  }

  @Override
  public DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    List<Column> columns = columnsBuilder.build(config);
    GroupBy groupBy = Optional.ofNullable(havingConditions)
        .map(conditions -> new GroupBy(columns, conditions.build(config)))
        .orElse(new GroupBy(columns));
    return queryBuilder.buildDslQueryBuilder(config).groupBy(groupBy);
  }
}
