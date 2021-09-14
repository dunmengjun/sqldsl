package com.dmj.sqldsl.executor.visitor;

import com.dmj.sqldsl.executor.SqlDialect;
import com.dmj.sqldsl.executor.exception.UnsupportedSqlDialectException;

public class ModelVisitorFactory {

  public static ModelVisitor getModeVisitor(SqlDialect dialect) {
    switch (dialect) {
      case mysql:
      case h2:
        return new StandardModelVisitor();
      case postgresql:
        return new PostgresqlModelVisitor();
      case oracle:
        return new OracleModelVisitor();
      case sqlserver:
        return new SqlserverModelVisitor();
      default:
        throw new UnsupportedSqlDialectException(dialect);
    }
  }
}
