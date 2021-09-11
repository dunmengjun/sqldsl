package com.dmj.sqldsl.builder.condition;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.condition.ConditionElement;
import com.dmj.sqldsl.model.condition.Or;

public class OrBuilder implements JunctionBuilder {

  @Override
  public ConditionElement build(EntityConfig config) {
    return new Or();
  }
}
