package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface LongLambda<T> extends SerializableLambda<T, Long> {

  Long apply(T t);
}
