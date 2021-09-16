package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.FunctionColumnsBuilder;
import com.dmj.sqldsl.builder.condition.ConditionalExpression;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.GroupBy;
import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import java.util.Optional;

public abstract class GroupByBuilder implements ToDslQuery {

  protected ConditionalExpression havingConditions;
  protected FunctionColumnsBuilder columnsBuilder;


  public final GroupByBuilder having(ConditionalExpression havingConditions) {
    this.havingConditions = havingConditions;
    return this;
  }

  public GroupByLimitBuilder limit(int offset, int size) {
    return new GroupByLimitBuilder(this, offset, size);
  }

  public GroupByLimitBuilder limit(int size) {
    return new GroupByLimitBuilder(this, 0, size);
  }

  protected GroupBy buildGroupBy(EntityConfig config) {
    List<Column> columns = columnsBuilder.build(config);
    return Optional.ofNullable(havingConditions)
        .map(conditions -> new GroupBy(columns, conditions.build(config)))
        .orElse(new GroupBy(columns));
  }

  protected abstract DslQuery.DslQueryBuilder build(EntityConfig config);
}
