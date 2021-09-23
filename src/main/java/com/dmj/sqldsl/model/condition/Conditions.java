package com.dmj.sqldsl.model.condition;

import static java.util.Collections.emptyList;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Conditions implements ConditionElement {

  private List<ConditionElement> conditionElements;

  public static Conditions empty() {
    return new Conditions(emptyList());
  }

  public boolean isEmpty() {
    return conditionElements == null || conditionElements.isEmpty();
  }
}
