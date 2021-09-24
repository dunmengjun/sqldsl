package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.column.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Order {

  private Column column;
  private boolean isAsc;
}
