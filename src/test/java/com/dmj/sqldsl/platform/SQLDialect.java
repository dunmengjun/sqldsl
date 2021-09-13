package com.dmj.sqldsl.platform;

public enum SQLDialect {
  h2(""),
  mysql(";MODE=MYSQL"),
  postgresql(";MODE=PostgreSQL"),
  oracle(";MODE=Oracle"),
  sqlserver(";MODE=MSSQLServer");

  private final String dialect;

  SQLDialect(String dialect) {
    this.dialect = dialect;
  }

  public String getDialect() {
    return dialect;
  }
}
