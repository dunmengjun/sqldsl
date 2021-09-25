package com.dmj.sqldsl.builder.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EntityConfig {

  private TableConfig tableConfig;
  private ColumnConfig columnConfig;
  private NameTranslator lambdaMethodTranslator;
}
