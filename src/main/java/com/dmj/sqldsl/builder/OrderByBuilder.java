package com.dmj.sqldsl.builder;

import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.type.ColumnFunction;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Order;
import com.dmj.sqldsl.model.OrderBy;
import java.util.List;

public abstract class OrderByBuilder implements ToDslQuery {

  private final List<OrderBuilder> orderBuilders;

  public OrderByBuilder(List<OrderBuilder> orderBuilders) {
    this.orderBuilders = orderBuilders;
  }

  protected abstract DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config);

  protected DslQuery.DslQueryBuilder build(EntityConfig config) {
    List<Order> orders = orderBuilders.stream()
        .map(orderBuilder -> orderBuilder.build(config))
        .collect(toList());
    return buildDslQueryBuilder(config).orderBy(new OrderBy(orders));
  }

  public <T, R> OrderByBuilder orderBy(ColumnFunction<T, R> function, boolean isAsc) {
    this.orderBuilders.add(new OrderBuilder(function, isAsc));
    return this;
  }

  public LimitBuilder limit(int offset, int size) {
    return new OrderByLimitBuilder(this, offset, size);
  }

  public LimitBuilder limit(int size) {
    return new OrderByLimitBuilder(this, 0, size);
  }

  @Override
  public DslQuery toQuery(EntityConfig config) {
    return this.build(config).build();
  }
}
