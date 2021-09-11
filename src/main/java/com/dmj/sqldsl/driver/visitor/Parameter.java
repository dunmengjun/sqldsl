package com.dmj.sqldsl.driver.visitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Parameter {

  private Class<?> type;
  private Object value;
}
