package com.dmj.sqldsl.builder;

import static com.dmj.sqldsl.utils.EntityClassUtils.getTableName;

import com.dmj.sqldsl.builder.annotation.Alias;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.config.TableAnnotation;
import com.dmj.sqldsl.model.Join;
import com.dmj.sqldsl.model.JoinFlag;
import com.dmj.sqldsl.model.SimpleTable;
import com.dmj.sqldsl.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinBuilder {

  private JoinFlag flag;
  private Class<?> entityClass;
  private ConditionsBuilder conditionsBuilder;

  protected Join build(EntityConfig config) {
    SimpleTable simpleTable = buildSimpleTable(config.getTableAnnotation(), entityClass);
    return new Join(flag, simpleTable, conditionsBuilder.build(config));
  }

  private SimpleTable buildSimpleTable(TableAnnotation config, Class<?> entityClass) {
    String tableName = getTableName(config, entityClass);
    if (entityClass.isAnnotationPresent(Alias.class)) {
      String alias = entityClass.getAnnotation(Alias.class).value();
      if (StringUtils.isBlank(alias)) {
        alias = entityClass.getSimpleName();
      }
      return new SimpleTable(tableName, alias);
    }
    return new SimpleTable(tableName);
  }
}
