package com.dmj.sqldsl.platform;

import com.dmj.sqldsl.driver.SqlDialect;

public enum H2Mode {
  h2(""),
  mysql(";MODE=MYSQL"),
  postgresql(";MODE=PostgreSQL"),
  oracle(";MODE=Oracle"),
  sqlserver(";MODE=MSSQLServer");

  private final String dialect;

  H2Mode(String dialect) {
    this.dialect = dialect;
  }

  public String getDialect() {
    return dialect;
  }

  public SqlDialect toSqlDialect() {
    return SqlDialect.valueOf(this.name());
  }
}
