package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class GroupBy {

  @Getter
  private List<Column> columns;

  public boolean isEmpty() {
    return columns == null || columns.isEmpty();
  }
}
