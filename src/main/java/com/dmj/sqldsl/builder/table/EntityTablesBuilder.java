package com.dmj.sqldsl.builder.table;

import static com.dmj.sqldsl.utils.EntityClassUtils.getTableName;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.SimpleTable;
import com.dmj.sqldsl.model.Table;
import java.util.List;
import java.util.stream.Collectors;

public class EntityTablesBuilder implements TablesBuilder {

  private final List<Class<?>> entityClasses;

  public EntityTablesBuilder(List<Class<?>> entityClasses) {
    this.entityClasses = entityClasses;
  }

  @Override
  public List<Table> build(EntityConfig config) {
    return entityClasses.stream()
        .map(entityClass ->
            new SimpleTable(getTableName(config.getTableAnnotation(), entityClass)))
        .collect(Collectors.toList());
  }
}
