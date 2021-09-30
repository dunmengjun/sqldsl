package com.dmj.sqldsl.executor.exception;

import com.dmj.sqldsl.model.condition.ConditionElement;
import lombok.Getter;

@Getter
public class UnsupportedConditionException extends RuntimeException {

  private final transient ConditionElement element;

  public UnsupportedConditionException(ConditionElement element) {
    super("Unsupported condition: " + element);
    this.element = element;
  }
}
