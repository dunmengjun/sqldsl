package com.dmj.sqldsl.builder.condition;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.condition.ConditionElement;

public interface ConditionElementBuilder {

  ConditionElement build(EntityConfig config);
}
