package com.dmj.sqldsl.builder.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class EntityConfig {

  private TableConfig tableConfig;
  private ColumnConfig columnConfig;

  @Setter
  private NameTranslator lambdaMethodTranslator;
}
