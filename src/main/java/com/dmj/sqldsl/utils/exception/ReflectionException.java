package com.dmj.sqldsl.utils.exception;

import lombok.Getter;

@Getter
public class ReflectionException extends RuntimeException {

  private Exception exception;

  public ReflectionException(Exception exception) {
    super(exception);
    this.exception = exception;
  }

  public ReflectionException(String message) {
    super(message);
  }
}
