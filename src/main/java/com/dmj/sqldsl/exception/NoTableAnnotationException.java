package com.dmj.sqldsl.exception;

import lombok.Getter;

import java.lang.annotation.Annotation;

@Getter
public class NoTableAnnotationException extends RuntimeException {

    private final Class<? extends Annotation> annotationClass;
    private final Class<?> entityClass;

    public NoTableAnnotationException(Class<?> entityClass,
                                      Class<? extends Annotation> annotationClass) {
        super(String.format("Not found table annotation(%s) in entity class(%s)",
                annotationClass.toString(), entityClass.toString()));
        this.entityClass = entityClass;
        this.annotationClass = annotationClass;
    }
}
