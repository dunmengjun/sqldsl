package com.dmj.sqldsl.utils.exception;

import lombok.Getter;

@Getter
public class ReflectionException extends RuntimeException {

  private final Exception exception;

  public ReflectionException(Exception exception) {
    super(exception);
    this.exception = exception;
  }
}
