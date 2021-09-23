package com.dmj.sqldsl.model;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderBy {

  private List<Order> orders;

  public boolean isEmpty() {
    return orders == null || orders.isEmpty();
  }
}
