package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.condition.Conditions;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Join {

  private JoinFlag flag;
  private Table table;
  private Conditions conditions;
}
