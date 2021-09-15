package com.dmj.sqldsl.model.condition;

import com.dmj.sqldsl.model.column.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Condition implements ConditionElement {

  private Column left;
  private ConditionMethod method;
  private Column right;
}
