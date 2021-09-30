package com.dmj.sqldsl.builder.exception;

public class UnknownException extends RuntimeException {

  public UnknownException() {
    super("Unknown error");
  }
}
