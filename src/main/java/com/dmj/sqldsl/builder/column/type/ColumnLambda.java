package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface ColumnLambda<T, R> extends SerializableLambda {

  R apply(T t);
}
