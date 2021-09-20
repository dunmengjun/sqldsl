package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface BooleanLambda<T> extends SerializableLambda<T, Boolean> {

  Boolean apply(T t);
}
