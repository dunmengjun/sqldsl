package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Builder
public class SelectFrom {

  @Getter
  private List<Column> columns;
  @Getter
  private List<Table> tables;
  private List<Join> joins;

  public Optional<List<Join>> getJoins() {
    return Optional.ofNullable(joins).filter(x -> !x.isEmpty());
  }
}
