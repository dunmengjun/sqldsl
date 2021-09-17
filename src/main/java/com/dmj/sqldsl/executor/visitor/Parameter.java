package com.dmj.sqldsl.executor.visitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Parameter {

  private Object value;

  @Override
  public String toString() {
    return String.format("%s (%s)", value, value.getClass().getSimpleName());
  }
}
