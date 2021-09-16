package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.FunctionColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnFunction;
import com.dmj.sqldsl.builder.condition.ConditionalExpression;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import java.util.Arrays;

public class WhereBuilder implements ToDslQuery {

  private final FromBuilder fromBuilder;
  private final ConditionalExpression conditionalExpression;

  public WhereBuilder(FromBuilder fromBuilder, ConditionalExpression conditionalExpression) {
    this.fromBuilder = fromBuilder;
    this.conditionalExpression = conditionalExpression;
  }

  public WhereLimitBuilder limit(int offset, int size) {
    return new WhereLimitBuilder(this, offset, size);
  }

  public WhereLimitBuilder limit(int size) {
    return new WhereLimitBuilder(this, 0, size);
  }

  @SafeVarargs
  public final <T, R> WhereGroupByBuilder groupBy(ColumnFunction<T, R>... functions) {
    return new WhereGroupByBuilder(this, new FunctionColumnsBuilder(Arrays.asList(functions)));
  }

  protected DslQuery.DslQueryBuilder build(EntityConfig config) {
    return fromBuilder.build(config).conditions(conditionalExpression.build(config));
  }

  public DslQuery toQuery(EntityConfig config) {
    return this.build(config).build();
  }
}
