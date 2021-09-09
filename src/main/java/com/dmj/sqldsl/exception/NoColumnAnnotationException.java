package com.dmj.sqldsl.exception;

import lombok.Getter;

import java.lang.annotation.Annotation;

@Getter
public class NoColumnAnnotationException extends RuntimeException {

    private final Class<? extends Annotation> annotationClass;
    private final Class<?> entityClass;

    public NoColumnAnnotationException(Class<?> entityClass,
                                       Class<? extends Annotation> annotationClass) {
        super(String.format("Not found column annotation(%s) in entity class(%s)",
                annotationClass.toString(), entityClass.toString()));
        this.entityClass = entityClass;
        this.annotationClass = annotationClass;
    }
}
