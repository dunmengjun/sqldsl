package com.dmj.sqldsl.builder.column;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.SerializableLambda;
import com.dmj.sqldsl.model.column.Function;
import java.util.Arrays;
import java.util.List;

public class ColumnBuilders {

  public static <T, R> ColumnBuilder<T, Long> count(ColumnLambda<T, R> lambda) {
    return new FunctionColumnBuilder<>(Function.count, singletonList(lambda.getColumnBuilder()));
  }

  public static <T, R> ColumnBuilder<T, Long> count(ColumnBuilder<T, R> columnBuilder) {
    return new FunctionColumnBuilder<>(Function.count, singletonList(columnBuilder));
  }

  public static <T, R> ColumnBuilder<T, Long> sum(ColumnLambda<T, R> lambda) {
    return new FunctionColumnBuilder<>(Function.sum, singletonList(lambda.getColumnBuilder()));
  }

  public static <T> ColumnBuilder<T, Number> avg(ColumnLambda<T, Number> lambda) {
    return new FunctionColumnBuilder<>(Function.avg, singletonList(lambda.getColumnBuilder()));
  }

  public static <T> ColumnBuilder<T, Number> max(ColumnLambda<T, Number> lambda) {
    return new FunctionColumnBuilder<>(Function.avg, singletonList(lambda.getColumnBuilder()));
  }

  public static <T> ColumnBuilder<T, Number> min(ColumnLambda<T, Number> lambda) {
    return new FunctionColumnBuilder<>(Function.min, singletonList(lambda.getColumnBuilder()));
  }

  @SafeVarargs
  public static <T, R> ColumnBuilder<T, R> distinct(ColumnLambda<T, R>... lambdas) {
    List<ColumnBuilder<?, ?>> params = Arrays.stream(lambdas)
        .map(SerializableLambda::getColumnBuilder)
        .collect(toList());
    return new FunctionColumnBuilder<>(Function.distinct, params);
  }
}
