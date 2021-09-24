package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.Order;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true)
public class OrderBuilder {

  private ColumnBuilder<?, ?> columnBuilder;
  private boolean isAsc;

  protected Order build(EntityConfig config) {
    return new Order(columnBuilder.build(config), isAsc);
  }
}
