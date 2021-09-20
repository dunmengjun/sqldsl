package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface NumberLambda<T> extends SerializableLambda<T, Number> {

  Number apply(T t);

}
