package com.dmj.sqldsl.executor.exception;

import com.dmj.sqldsl.model.column.Column;
import lombok.Getter;

@Getter
public class NotSupportedColumnException extends RuntimeException {

  private final Column column;

  public NotSupportedColumnException(Column column) {
    super("Not supported column: " + column);
    this.column = column;
  }
}
