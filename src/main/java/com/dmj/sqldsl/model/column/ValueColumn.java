package com.dmj.sqldsl.model.column;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValueColumn implements Column {

  private Object value;

  @Override
  public String getTableName() {
    return "";
  }

  @Override
  public String getName() {
    return "";
  }
}