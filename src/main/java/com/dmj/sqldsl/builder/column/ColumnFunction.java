package com.dmj.sqldsl.builder.column;

import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;

import java.io.Serializable;

@FunctionalInterface
public interface ColumnFunction<T, R> extends Serializable {

  R apply(T t);

  default ColumnBuilder getColumnBuilder() {
    return new LambdaColumnBuilder(invokeMethod("writeReplace", this));
  }
}
