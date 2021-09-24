package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface StringLambda<T> extends TypedLambda<T, String> {

  String apply(T t);
}
