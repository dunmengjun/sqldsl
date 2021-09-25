package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface StringLambda<T> extends ColumnLambda<T, String> {

  String apply(T t);
}
