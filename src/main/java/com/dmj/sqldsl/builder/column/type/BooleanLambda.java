package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface BooleanLambda<T> extends SerializableLambda {

  Boolean apply(T t);
}
