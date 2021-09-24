package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface LongLambda<T> extends TypedLambda<T, Long> {

  Long apply(T t);
}
