package com.dmj.sqldsl.executor.visitor;

import com.dmj.sqldsl.builder.Limit;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.SelectFrom;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import java.util.ArrayList;
import java.util.List;

public class OracleModelVisitor extends StandardModelVisitor {

  private static final String PAGE_COLUMN_NAME = "rownum";
  private static final String PAGE_COLUMN_ALIAS = "rn";

  @Override
  protected String visit(SelectFrom selectFrom) {
    List<Column> columns = new ArrayList<>(selectFrom.getColumns());
    columns.add(
        SimpleColumn.builder()
            .name(PAGE_COLUMN_NAME)
            .alias(PAGE_COLUMN_ALIAS)
            .build()
    );
    selectFrom.setColumns(columns);
    return super.visit(selectFrom);
  }

  @Override
  public String visit(DslQuery query) {
    String selectFromWhere = visitSelectFromWhere(query);
    return query.getLimit().map(limit ->
            String.format("select m.* from (%s) m where %s between %s and %s",
                selectFromWhere,
                PAGE_COLUMN_ALIAS,
                limit.getOffset(),
                limit.getSize() + limit.getOffset()))
        .orElse(selectFromWhere);
  }

  @Override
  protected String visitLimit(String allSqlWithoutLimit, Limit limit) {
    return String.format("select m.* from (%s) m where %s between %s and %s",
        allSqlWithoutLimit,
        PAGE_COLUMN_ALIAS,
        limit.getOffset(),
        limit.getSize() + limit.getOffset());
  }
}