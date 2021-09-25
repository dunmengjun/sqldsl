package com.dmj.sqldsl.builder.column;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.model.column.Function;
import java.util.Arrays;
import java.util.List;

public class ColumnBuilders {

  public static <T, R> ColumnBuilder<T, R> all() {
    return new AllColumnBuilder<>();
  }

  public static <T, R> ColumnBuilder<T, Long> count(ColumnLambda<T, R> lambda) {
    return new FunctionColumnBuilder<>(Function.count, singletonList(lambda.getColumnBuilder()));
  }

  public static <T, R> ColumnBuilder<T, Long> count(ColumnBuilder<T, R> columnBuilder) {
    return new FunctionColumnBuilder<>(Function.count, singletonList(columnBuilder));
  }

  public static <T, R> ColumnBuilder<T, Long> sum(ColumnLambda<T, R> lambda) {
    return new FunctionColumnBuilder<>(Function.sum, singletonList(lambda.getColumnBuilder()));
  }

  public static <T, R> ColumnBuilder<T, Long> sum(ColumnBuilder<T, R> columnBuilder) {
    return new FunctionColumnBuilder<>(Function.sum, singletonList(columnBuilder));
  }

  public static <T> ColumnBuilder<T, Number> avg(ColumnLambda<T, Number> lambda) {
    return new FunctionColumnBuilder<>(Function.avg, singletonList(lambda.getColumnBuilder()));
  }

  public static <T, R> ColumnBuilder<T, Long> avg(ColumnBuilder<T, R> columnBuilder) {
    return new FunctionColumnBuilder<>(Function.avg, singletonList(columnBuilder));
  }

  public static <T> ColumnBuilder<T, Number> max(ColumnLambda<T, Number> lambda) {
    return new FunctionColumnBuilder<>(Function.max, singletonList(lambda.getColumnBuilder()));
  }

  public static <T, R> ColumnBuilder<T, Long> max(ColumnBuilder<T, R> columnBuilder) {
    return new FunctionColumnBuilder<>(Function.max, singletonList(columnBuilder));
  }

  public static <T> ColumnBuilder<T, Number> min(ColumnLambda<T, Number> lambda) {
    return new FunctionColumnBuilder<>(Function.min, singletonList(lambda.getColumnBuilder()));
  }

  public static <T, R> ColumnBuilder<T, Long> min(ColumnBuilder<T, R> columnBuilder) {
    return new FunctionColumnBuilder<>(Function.min, singletonList(columnBuilder));
  }

  @SafeVarargs
  public static <T, R> ColumnBuilder<T, R> distinct(ColumnLambda<T, R>... lambdas) {
    List<ColumnBuilder<?, ?>> params = Arrays.stream(lambdas)
        .map(ColumnLambda::getColumnBuilder)
        .collect(toList());
    return new FunctionColumnBuilder<>(Function.distinct, params);
  }
}
