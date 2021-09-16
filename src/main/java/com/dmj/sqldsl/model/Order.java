package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.column.Column;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Order {

  private Column column;
  private boolean isAsc;
}
