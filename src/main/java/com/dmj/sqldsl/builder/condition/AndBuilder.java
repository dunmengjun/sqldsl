package com.dmj.sqldsl.builder.condition;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.condition.And;
import com.dmj.sqldsl.model.condition.ConditionElement;

public class AndBuilder implements JunctionBuilder {

  @Override
  public ConditionElement build(EntityConfig config) {
    return new And();
  }
}
