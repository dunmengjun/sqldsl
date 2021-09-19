package com.dmj.sqldsl.builder.table;

import static com.dmj.sqldsl.utils.EntityClassUtils.getTableName;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.SimpleTable;
import com.dmj.sqldsl.model.Table;

public class EntityTableBuilder implements TableBuilder {

  private final Class<?> entityClass;

  public EntityTableBuilder(Class<?> entityClass) {
    this.entityClass = entityClass;
  }

  @Override
  public Table build(EntityConfig config) {
    return new SimpleTable(getTableName(config.getTableAnnotation(), entityClass));
  }
}
