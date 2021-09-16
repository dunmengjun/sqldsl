package com.dmj.sqldsl.builder;

import static com.dmj.sqldsl.utils.EntityClassUtils.getTableName;

import com.dmj.sqldsl.builder.condition.ConditionalExpression;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.Join;
import com.dmj.sqldsl.model.JoinFlag;
import com.dmj.sqldsl.model.SimpleTable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinBuilder {

  private JoinFlag flag;
  private Class<?> entityClass;
  private ConditionalExpression conditionalExpression;

  protected Join build(EntityConfig config) {
    String tableName = getTableName(config.getTableAnnotation(), entityClass);
    return new Join(flag, new SimpleTable(tableName), conditionalExpression.build(config));
  }
}
