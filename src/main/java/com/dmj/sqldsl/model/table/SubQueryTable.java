package com.dmj.sqldsl.model.table;

import com.dmj.sqldsl.model.DslQuery;
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
}
