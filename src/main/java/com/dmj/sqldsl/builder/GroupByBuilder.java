package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.FunctionColumnsBuilder;
import com.dmj.sqldsl.builder.condition.ConditionalExpression;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.GroupBy;
import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import java.util.Optional;

public class GroupByBuilder implements ToDslQuery {

  private final FromBuilder fromBuilder;
  private final FunctionColumnsBuilder columnsBuilder;
  private ConditionalExpression havingConditions;

  public GroupByBuilder(FromBuilder fromBuilder, FunctionColumnsBuilder columnsBuilder) {
    this.fromBuilder = fromBuilder;
    this.columnsBuilder = columnsBuilder;
  }

  public final GroupByBuilder having(ConditionalExpression havingConditions) {
    this.havingConditions = havingConditions;
    return this;
  }

  @Override
  public DslQuery toQuery(EntityConfig config) {
    List<Column> columns = columnsBuilder.build(config);
    GroupBy groupBy = Optional.ofNullable(havingConditions)
        .map(conditions -> new GroupBy(columns, conditions.build(config)))
        .orElse(new GroupBy(columns));
    return DslQuery.builder()
        .selectFrom(fromBuilder.build(config))
        .groupBy(groupBy)
        .build();
  }
}
