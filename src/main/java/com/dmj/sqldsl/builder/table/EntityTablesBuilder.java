package com.dmj.sqldsl.builder.table;

import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.NoTableAnnotationException;
import com.dmj.sqldsl.model.SimpleTable;
import com.dmj.sqldsl.model.Table;
import com.dmj.sqldsl.utils.StringUtils;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public class EntityTablesBuilder implements TablesBuilder {

  private final List<Class<?>> entityClasses;

  public EntityTablesBuilder(List<Class<?>> entityClasses) {
    this.entityClasses = entityClasses;
  }

  @Override
  public List<Table> build(EntityConfig config) {
    return entityClasses.stream().map(entityClass -> {
      Class<? extends Annotation> tableClass = config.getTableAnnotation().getAnnotationClass();
      if (!entityClass.isAnnotationPresent(tableClass)) {
        throw new NoTableAnnotationException(entityClass, tableClass);
      }
      String tableName = invokeMethod(config.getTableAnnotation().getTableNameAttribute(),
          entityClass.getAnnotation(tableClass));
      if (StringUtils.isBlank(tableName)) {
        tableName = entityClass.getSimpleName();
      }
      return new SimpleTable(tableName);
    }).collect(Collectors.toList());
  }
}
