package com.dmj.sqldsl.executor.exception;

import com.dmj.sqldsl.model.condition.ConditionMethod;
import lombok.Getter;

@Getter
public class UnsupportedConditionMethodException extends RuntimeException {

  private final ConditionMethod method;

  public UnsupportedConditionMethodException(ConditionMethod method) {
    super("Unsupported condition method: " + method);
    this.method = method;
  }
}
