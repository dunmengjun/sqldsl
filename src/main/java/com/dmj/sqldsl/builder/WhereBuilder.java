package com.dmj.sqldsl.builder;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.SerializableLambda;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import java.util.Arrays;
import java.util.List;

public class WhereBuilder implements DslQueryBuilder {

  private final FromBuilder fromBuilder;
  private final ConditionsBuilder conditionsBuilder;

  public WhereBuilder(FromBuilder fromBuilder, ConditionsBuilder conditionsBuilder) {
    this.fromBuilder = fromBuilder;
    this.conditionsBuilder = conditionsBuilder;
  }

  public LimitBuilder limit(int offset, int size) {
    return new WhereLimitBuilder(this, offset, size);
  }

  public LimitBuilder limit(int size) {
    return new WhereLimitBuilder(this, 0, size);
  }

  @SafeVarargs
  public final <T, R> GroupByBuilder groupBy(ColumnLambda<T, R>... functions) {
    List<ColumnBuilder<?, ?>> columnBuilders = Arrays.stream(functions)
        .map(SerializableLambda::getColumnBuilder).collect(toList());
    return new WhereGroupByBuilder(this, new NormalColumnsBuilder(columnBuilders));
  }

  @SafeVarargs
  public final <T, R> GroupByBuilder groupBy(ColumnBuilder<T, R>... columnBuilders) {
    return new WhereGroupByBuilder(this, new NormalColumnsBuilder(asList(columnBuilders)));
  }

  protected DslQuery.DslQueryBuilder build(EntityConfig config) {
    return fromBuilder.build(config).conditions(conditionsBuilder.build(config));
  }

  public DslQuery toQuery(EntityConfig config) {
    return this.build(config).build();
  }
}
