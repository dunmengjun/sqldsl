package com.dmj.sqldsl.executor.exception;

import com.dmj.sqldsl.executor.SqlDialect;
import lombok.Getter;

@Getter
public class UnsupportedSqlDialectException extends RuntimeException {

  private final SqlDialect dialect;

  public UnsupportedSqlDialectException(SqlDialect dialect) {
    super("Unsupported sql dialect: " + dialect);
    this.dialect = dialect;
  }
}
