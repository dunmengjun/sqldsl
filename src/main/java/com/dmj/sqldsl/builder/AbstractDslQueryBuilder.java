package com.dmj.sqldsl.builder;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.SerializableLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Order;
import com.dmj.sqldsl.model.OrderBy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDslQueryBuilder implements DslQueryBuilder {

  private final List<OrderBuilder> orderBuilders;

  public AbstractDslQueryBuilder() {
    this.orderBuilders = new ArrayList<>();
  }

  @Override
  public <T, R> DslQueryBuilder orderBy(ColumnBuilder<T, R> columnBuilder, boolean isAsc) {
    this.orderBuilders.add(new OrderBuilder(columnBuilder, isAsc));
    return this;
  }

  @Override
  public <T, R> DslQueryBuilder orderBy(ColumnLambda<T, R> function, boolean isAsc) {
    this.orderBuilders.add(new OrderBuilder(function.getColumnBuilder(), isAsc));
    return this;
  }

  @SafeVarargs
  public final <T, R> GroupByBuilder groupBy(ColumnLambda<T, R>... functions) {
    List<ColumnBuilder<?, ?>> columnBuilders = Arrays.stream(functions)
        .map(SerializableLambda::getColumnBuilder).collect(toList());
    return new GroupByBuilder(this, new NormalColumnsBuilder(columnBuilders));
  }

  public final <T> GroupByBuilder groupBy(Collection<ColumnLambda<T, ?>> functions) {
    List<ColumnBuilder<?, ?>> columnBuilders = functions.stream()
        .map(SerializableLambda::getColumnBuilder)
        .collect(Collectors.toList());
    return new GroupByBuilder(this, new NormalColumnsBuilder(columnBuilders));
  }

  @SafeVarargs
  public final <T, R> GroupByBuilder groupBy(ColumnBuilder<T, R>... columnBuilders) {
    return new GroupByBuilder(this, new NormalColumnsBuilder(asList(columnBuilders)));
  }

  @Override
  public LimitBuilder limit(int offset, int size) {
    return new LimitBuilder(this, offset, size);
  }

  @Override
  public LimitBuilder limit(int size) {
    return new LimitBuilder(this, 0, size);
  }

  @Override
  public DslQuery toQuery(EntityConfig config) {
    List<Order> orders = orderBuilders.stream()
        .map(orderBuilder -> orderBuilder.build(config))
        .collect(toList());
    return buildDslQueryBuilder(config).orderBy(new OrderBy(orders)).build();
  }

  public abstract DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config);
}
