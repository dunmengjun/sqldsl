package com.dmj.sqldsl.builder.exception;

public class UnmatchedTypeException extends RuntimeException {

  private Class<?> expectedType;
  private Class<?> actualType;

  public UnmatchedTypeException(Class<?> expectedType, Class<?> actualType) {
    super(String.format("unmatched type, expected type: %s, actual type: %s",
        expectedType, actualType));
    this.expectedType = expectedType;
    this.actualType = actualType;
  }
}
