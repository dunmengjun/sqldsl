package com.dmj.sqldsl.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class OrderBy {

  @Getter
  private List<Order> orders;

  public boolean isEmpty() {
    return orders == null || orders.isEmpty();
  }
}
