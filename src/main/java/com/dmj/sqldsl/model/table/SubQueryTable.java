package com.dmj.sqldsl.model.table;

import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SubQueryTable implements Table {

  private DslQuery query;

  private String alias;

  @Override
  public String getName() {
    return alias;
  }

  @Override
  public List<Column> getColumns() {
    return query.getSelectFrom().getColumns();
  }
}
