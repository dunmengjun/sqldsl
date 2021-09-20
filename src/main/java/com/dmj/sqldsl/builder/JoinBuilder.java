package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.table.TableBuilder;
import com.dmj.sqldsl.model.Join;
import com.dmj.sqldsl.model.JoinFlag;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class JoinBuilder {

  @Exclude
  private JoinFlag flag;

  private TableBuilder tableBuilder;

  @Exclude
  private ConditionsBuilder conditionsBuilder;

  protected Join build(EntityConfig config) {
    return new Join(flag, tableBuilder.build(config), conditionsBuilder.build(config));
  }
}
