package com.dmj.sqldsl.builder.column.type;

import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.LambdaColumnBuilder;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;

@FunctionalInterface
public interface ColumnFunction<T, R> extends Serializable {

  R apply(T t);

  default ColumnBuilder getColumnBuilder() {
    return new LambdaColumnBuilder(invokeMethod("writeReplace", this));
  }

  default String getMethodName() {
    SerializedLambda lambda = invokeMethod("writeReplace", this);
    return lambda.getImplMethodName();
  }
}