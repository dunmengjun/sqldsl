package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.column.ValueColumn;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Entity {

  private String tableName;
  private ValueColumn id;
  private List<ValueColumn> columns;

  public boolean hasId() {
    return id != null && id.getValue() != null;
  }
}
