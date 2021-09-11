package com.dmj.sqldsl.utils.exception;

import lombok.Getter;

@Getter
public class ReflectionException extends RuntimeException {

  private final ReflectiveOperationException exception;

  public ReflectionException(ReflectiveOperationException exception) {
    super(exception);
    this.exception = exception;
  }
}
