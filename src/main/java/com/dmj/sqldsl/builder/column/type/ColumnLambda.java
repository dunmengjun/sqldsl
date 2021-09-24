package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface ColumnLambda<T, R> extends TypedLambda<T, R> {

  R apply(T t);
}
