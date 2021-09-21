package com.dmj.sqldsl.builder.column;

import static java.util.Collections.singletonList;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.model.column.Function;

public class ColumnBuilders {

  public static <T, R> ColumnBuilder<T, Long> count(ColumnLambda<T, R> lambda) {
    return new FunctionColumnBuilder<>(Function.count, singletonList(lambda.getColumnBuilder()));
  }

  public static <T, R> ColumnBuilder<T, Long> sum(ColumnLambda<T, R> lambda) {
    return new FunctionColumnBuilder<>(Function.sum, singletonList(lambda.getColumnBuilder()));
  }

  public static <T> ColumnBuilder<T, Number> avg(ColumnLambda<T, Number> lambda) {
    return new FunctionColumnBuilder<>(Function.avg, singletonList(lambda.getColumnBuilder()));
  }
}
