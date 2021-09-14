package com.dmj.sqldsl.executor.visitor;

import com.dmj.sqldsl.builder.Limit;

/**
 * 只兼容sqlserver 2012版本以上
 */
public class SqlserverModelVisitor extends StandardModelVisitor {

  @Override
  protected String visit(Limit limit) {
    return String.format(" offset %s rows fetch next %s rows only",
        limit.getOffset() * limit.getSize(), limit.getSize());
  }
}
