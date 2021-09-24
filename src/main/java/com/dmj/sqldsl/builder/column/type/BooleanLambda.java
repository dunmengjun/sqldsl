package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface BooleanLambda<T> extends TypedLambda<T, Boolean> {

  Boolean apply(T t);
}
