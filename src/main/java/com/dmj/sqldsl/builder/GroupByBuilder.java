package com.dmj.sqldsl.builder;

import static com.dmj.sqldsl.utils.CollectionUtils.asModifiableList;

import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.condition.ConditionalExpression;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.GroupBy;
import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import java.util.Optional;

public abstract class GroupByBuilder implements ToDslQuery {

  protected ConditionalExpression havingConditions;
  protected NormalColumnsBuilder columnsBuilder;


  public final GroupByBuilder having(ConditionalExpression havingConditions) {
    this.havingConditions = havingConditions;
    return this;
  }

  public LimitBuilder limit(int offset, int size) {
    return new GroupByLimitBuilder(this, offset, size);
  }

  public LimitBuilder limit(int size) {
    return new GroupByLimitBuilder(this, 0, size);
  }

  public <T, R> OrderByBuilder orderBy(ColumnLambda<T, R> function, boolean isAsc) {
    return new GroupOrderByBuilder(this,
        asModifiableList(new OrderBuilder(function, isAsc)));
  }

  protected GroupBy buildGroupBy(EntityConfig config) {
    List<Column> columns = columnsBuilder.build(config);
    return Optional.ofNullable(havingConditions)
        .map(conditions -> new GroupBy(columns, conditions.build(config)))
        .orElse(new GroupBy(columns));
  }

  protected DslQuery.DslQueryBuilder build(EntityConfig config) {
    return buildDslQueryBuilder(config).groupBy(this.buildGroupBy(config));
  }

  @Override
  public DslQuery toQuery(EntityConfig config) {
    return this.build(config).build();
  }

  protected abstract DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config);
}
