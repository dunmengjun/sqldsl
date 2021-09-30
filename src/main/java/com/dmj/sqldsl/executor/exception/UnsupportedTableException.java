package com.dmj.sqldsl.executor.exception;

import com.dmj.sqldsl.model.table.Table;
import lombok.Getter;

@Getter
public class UnsupportedTableException extends RuntimeException {

  private final transient Table table;

  public UnsupportedTableException(Table table) {
    super("Unsupported column: " + table);
    this.table = table;
  }
}
