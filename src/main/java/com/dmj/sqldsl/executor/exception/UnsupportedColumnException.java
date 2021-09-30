package com.dmj.sqldsl.executor.exception;

import com.dmj.sqldsl.model.column.Column;
import lombok.Getter;

@Getter
public class UnsupportedColumnException extends RuntimeException {

  private final transient Column column;

  public UnsupportedColumnException(Column column) {
    super("Unsupported column: " + column);
    this.column = column;
  }
}
