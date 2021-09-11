package com.dmj.sqldsl.builder.config;

import java.lang.annotation.Annotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ColumnAnnotation {

  private Class<? extends Annotation> annotationClass;
  private String columnNameAttribute;
}
