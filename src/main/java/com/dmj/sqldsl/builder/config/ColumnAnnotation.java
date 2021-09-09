package com.dmj.sqldsl.builder.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.lang.annotation.Annotation;

@AllArgsConstructor
@Builder
@Getter
public class ColumnAnnotation {
    private Class<? extends Annotation> annotationClass;
    private String columnNameAttribute;
}
