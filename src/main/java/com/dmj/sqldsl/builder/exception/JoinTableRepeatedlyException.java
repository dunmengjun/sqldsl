package com.dmj.sqldsl.builder.exception;

public class JoinTableRepeatedlyException extends RuntimeException {

  public JoinTableRepeatedlyException() {
    super("Can not join the same table repeatedly!");
  }
}
