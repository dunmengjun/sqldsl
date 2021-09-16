package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.type.ColumnFunction;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.Order;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderBuilder {

  private ColumnFunction<?, ?> function;
  private boolean isAsc;

  protected Order build(EntityConfig config) {
    return new Order(function.getColumnBuilder().build(config), isAsc);
  }
}
