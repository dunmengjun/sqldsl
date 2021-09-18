package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface NumberLambda<T> extends SerializableLambda {

  Number apply(T t);

}
