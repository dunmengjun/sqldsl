package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.table.Table;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class SelectFrom {

  @Getter
  @Setter
  private List<Column> columns;
  @Getter
  private Table table;
  private List<Join> joins;

  public Optional<List<Join>> getJoins() {
    return Optional.ofNullable(joins).filter(x -> !x.isEmpty());
  }
}
