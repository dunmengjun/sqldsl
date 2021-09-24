package com.dmj.sqldsl.builder.column.type;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(doNotUseGetters = true)
public class LambdaType {

  private Class<?> targetClass;
  private String methodName;
}
