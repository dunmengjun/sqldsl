package com.dmj.sqldsl.builder.condition;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.condition.Condition;
import com.dmj.sqldsl.model.condition.ConditionElement;

public class ConditionBuilder implements ConditionElementBuilder {

  private final ColumnBuilder left;
  private final ColumnBuilder right;

  public ConditionBuilder(ColumnBuilder left, ColumnBuilder right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public ConditionElement build(EntityConfig config) {
    return new Condition(left.build(config), right.build(config));
  }
}
