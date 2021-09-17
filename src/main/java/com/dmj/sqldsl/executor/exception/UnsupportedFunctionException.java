package com.dmj.sqldsl.executor.exception;

import com.dmj.sqldsl.model.column.Function;
import lombok.Getter;

@Getter
public class UnsupportedFunctionException extends RuntimeException {

  private final Function function;

  public UnsupportedFunctionException(Function function) {
    super("Unsupported function: " + function);
    this.function = function;
  }
}
