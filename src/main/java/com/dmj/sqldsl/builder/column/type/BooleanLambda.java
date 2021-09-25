package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface BooleanLambda<T> extends ColumnLambda<T, Boolean> {

  Boolean apply(T t);
}
