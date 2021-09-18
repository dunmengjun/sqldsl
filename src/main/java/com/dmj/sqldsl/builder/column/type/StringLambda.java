package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface StringLambda<T> extends SerializableLambda {

  String apply(T t);
}
