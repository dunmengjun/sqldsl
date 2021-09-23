package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.condition.Conditions;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class GroupBy {

  @Getter
  private List<Column> columns;
  private Conditions conditions;

  public GroupBy(List<Column> columns) {
    this.columns = columns;
  }

  public Optional<Conditions> getConditions() {
    if (conditions == null || conditions.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(conditions);
  }

  public boolean isEmpty() {
    return columns == null || columns.isEmpty();
  }
}
