package com.dmj.sqldsl.builder.condition;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.condition.ColumnValueCondition;
import com.dmj.sqldsl.model.condition.ConditionElement;

public class ConditionBuilder implements ConditionElementBuilder {

  private final ColumnBuilder leftColumn;
  private final Object value;

  public ConditionBuilder(ColumnBuilder leftColumn, Object value) {
    this.leftColumn = leftColumn;
    this.value = value;
  }

  @Override
  public ConditionElement build(EntityConfig config) {
    return new ColumnValueCondition(leftColumn.build(config), value);
  }
}
