package com.dmj.sqldsl.builder.column;

import java.io.Serializable;

import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;

@FunctionalInterface
public interface ColumnFunction<T> extends Serializable {

    T get();

    default ColumnBuilder getColumnBuilder() {
        return new LambdaColumnBuilder(invokeMethod("writeReplace", this));
    }
}
