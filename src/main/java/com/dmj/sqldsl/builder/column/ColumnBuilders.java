package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.FunctionType;
import com.dmj.sqldsl.builder.column.type.SubQueryType;
import com.dmj.sqldsl.model.column.Function;

public class ColumnBuilders {

  public static <T, R> FunctionType<T, Long> count(ColumnLambda<T, R> column) {
    return new FunctionType<>(Function.count, column);
  }

  public static <T, R> FunctionType<T, Long> sum(ColumnLambda<T, R> column) {
    return new FunctionType<>(Function.sum, column);
  }

  public static SubQueryType subQuery(DslQueryBuilder queryBuilder) {
    return new SubQueryType(queryBuilder);
  }
}
