package com.dmj.sqldsl.model.condition;

import com.dmj.sqldsl.model.column.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ColumnValueCondition implements Condition {

  private Column column;
  private Object value;
}
