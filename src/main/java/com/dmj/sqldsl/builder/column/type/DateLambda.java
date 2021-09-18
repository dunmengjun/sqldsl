package com.dmj.sqldsl.builder.column.type;

import java.util.Date;

@FunctionalInterface
public interface DateLambda<T> extends SerializableLambda {

  Date apply(T t);
}
