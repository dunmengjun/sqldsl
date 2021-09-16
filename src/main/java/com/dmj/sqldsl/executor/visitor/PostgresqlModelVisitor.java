package com.dmj.sqldsl.executor.visitor;

import com.dmj.sqldsl.model.Limit;

public class PostgresqlModelVisitor extends StandardModelVisitor {

  @Override
  protected String visit(Limit limit) {
    return String.format(" limit %s offset %s", limit.getSize(), limit.getOffset());
  }
}
