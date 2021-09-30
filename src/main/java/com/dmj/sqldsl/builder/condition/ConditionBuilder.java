package com.dmj.sqldsl.builder.condition;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.condition.Condition;
import com.dmj.sqldsl.model.condition.ConditionElement;
import com.dmj.sqldsl.model.condition.ConditionMethod;

public class ConditionBuilder implements ConditionElementBuilder {

  private final ColumnBuilder<?, ?> left;
  private final ConditionMethod method;
  private final ColumnBuilder<?, ?> right;

  public ConditionBuilder(ColumnBuilder<?, ?> left, ConditionMethod method,
      ColumnBuilder<?, ?> right) {
    this.left = left;
    this.method = method;
    this.right = right;
  }

  @Override
  public ConditionElement build(EntityConfig config) {
    return new Condition(left.build(config), method, right.build(config));
  }
}
