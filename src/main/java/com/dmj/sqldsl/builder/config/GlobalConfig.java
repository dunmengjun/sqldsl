package com.dmj.sqldsl.builder.config;

import javax.persistence.Column;
import javax.persistence.Table;

public class GlobalConfig {
    public static EntityConfig entityConfig = EntityConfig.builder()
            .tableAnnotation(new TableAnnotation(Table.class, "name"))
            .columnAnnotation(new ColumnAnnotation(Column.class, "name"))
            .build();

    public static boolean isValid() {
        return entityConfig != null;
    }
}
