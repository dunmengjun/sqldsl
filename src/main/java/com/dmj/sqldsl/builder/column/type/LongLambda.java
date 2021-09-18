package com.dmj.sqldsl.builder.column.type;

@FunctionalInterface
public interface LongLambda<T> extends SerializableLambda {

  Long apply(T t);
}
