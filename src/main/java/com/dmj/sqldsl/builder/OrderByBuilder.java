package com.dmj.sqldsl.builder;

import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Order;
import com.dmj.sqldsl.model.OrderBy;
import java.util.List;

public abstract class OrderByBuilder implements DslQueryBuilder {

  private final List<OrderBuilder> orderBuilders;

  public OrderByBuilder(List<OrderBuilder> orderBuilders) {
    this.orderBuilders = orderBuilders;
  }

  protected abstract DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config);

  @Override
  public DslQuery.DslQueryBuilder build(EntityConfig config) {
    List<Order> orders = orderBuilders.stream()
        .map(orderBuilder -> orderBuilder.build(config))
        .collect(toList());
    return buildDslQueryBuilder(config).orderBy(new OrderBy(orders));
  }

  public <T, R> OrderByBuilder orderBy(ColumnLambda<T, R> function, boolean isAsc) {
    this.orderBuilders.add(new OrderBuilder(function.getColumnBuilder(), isAsc));
    return this;
  }
}
