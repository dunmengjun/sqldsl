package com.dmj.sqldsl.builder.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EntityConfig {

  private TableAnnotation tableAnnotation;
  private ColumnAnnotation columnAnnotation;

  @Builder.Default
  private NameTranslator translator = new PojoLikedStyleTranslator();
}
