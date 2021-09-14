package com.dmj.sqldsl.executor.exception;

import com.dmj.sqldsl.model.condition.ConditionElement;
import lombok.Getter;

@Getter
public class NotSupportedConditionException extends RuntimeException {

  private final ConditionElement element;

  public NotSupportedConditionException(ConditionElement element) {
    super("Not supported condition: " + element);
    this.element = element;
  }
}
