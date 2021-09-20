package com.dmj.sqldsl.executor.visitor;

import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Limit;
import com.dmj.sqldsl.model.column.AliasColumn;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import java.util.ArrayList;
import java.util.List;

public class OracleModelVisitor extends StandardModelVisitor {

  private static final String PAGE_COLUMN_NAME = "rownum";
  private static final String PAGE_COLUMN_ALIAS = "rn";

  @Override
  public String visit(DslQuery query) {
    query.getLimit().ifPresent(x -> {
      List<Column> columns = new ArrayList<>(query.getSelectFrom().getColumns());
      columns.add(
          new AliasColumn(
              SimpleColumn.builder()
                  .name(PAGE_COLUMN_NAME)
                  .build(),
              PAGE_COLUMN_ALIAS)
      );
      query.getSelectFrom().setColumns(columns);
    });
    return super.visit(query);
  }

  @Override
  protected String visitLimit(String allSqlWithoutLimit, Limit limit) {
    return String.format("select m.* from (%s) m where m.%s between %s and %s",
        allSqlWithoutLimit,
        PAGE_COLUMN_ALIAS,
        limit.getOffset(),
        limit.getSize() + limit.getOffset());
  }
}
