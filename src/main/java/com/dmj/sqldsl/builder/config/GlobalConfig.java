package com.dmj.sqldsl.builder.config;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

public class GlobalConfig {

  public static EntityConfig entityConfig = EntityConfig.builder()
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
}
