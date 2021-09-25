package com.dmj.sqldsl.builder.config;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

public class GlobalConfig {

  private static EntityConfig entityConfig = EntityConfig.builder()
      .tableConfig(TableConfig.builder()
          .annotationClass(Table.class)
          .tableNameAttribute("name")
          .build())
      .columnConfig(ColumnConfig.builder()
          .idAnnotationClass(Id.class)
          .columnAnnotationClass(Column.class)
          .columnNameAttribute("name")
          .build())
      .lambdaMethodTranslator(new PojoLikedStyleTranslator())
      .build();

  public static boolean isValid() {
    return entityConfig != null;
  }

  public static void setEntityConfig(EntityConfig entityConfig) {
    GlobalConfig.entityConfig = entityConfig;
  }

  public static EntityConfig getEntityConfig() {
    return entityConfig;
  }

  public static void setGlobalColumnNameTranslator(NameTranslator translator) {
    entityConfig.getColumnConfig().setGlobalFieldNameTranslator(translator);
  }

  public static void setGlobalTableNameTranslator(NameTranslator translator) {
    entityConfig.getTableConfig().setGlobalTableNameTranslator(translator);
  }

  public static void setGlobalLambdaMethodTranslator(NameTranslator translator) {
    entityConfig.setLambdaMethodTranslator(translator);
  }
}
