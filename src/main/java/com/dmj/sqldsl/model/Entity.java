package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.column.ValueColumn;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Entity {

  private String tableName;
  private ValueColumn id;
  private List<ValueColumn> columns;
  private ModifiedFlag modifiedFlag;
}
