package com.dmj.sqldsl.builder.exception;

public class NoIdColumnException extends RuntimeException {

  public NoIdColumnException() {
    super("DELETE or UPDATE modified flag without id column!");
  }
}
