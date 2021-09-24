package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.condition.Conditions;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Where {

  private Conditions conditions;

  public boolean isEmpty() {
    return conditions == null || conditions.isEmpty();
  }
}
