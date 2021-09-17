package com.dmj.sqldsl.builder;

import static java.util.Arrays.asList;

import com.dmj.sqldsl.builder.column.EntityColumnsBuilder;
import com.dmj.sqldsl.builder.column.FunctionColumnBuilder;
import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.FunctionType;

public class DslQueryBuilder {

  @SafeVarargs
  public static <T, R> SelectBuilder selectAll(Class<T> entityClass,
      ColumnLambda<T, R>... excludeColumns) {
    return new SelectBuilder(new EntityColumnsBuilder(entityClass, asList(excludeColumns)));
  }

  @SafeVarargs
  public static <T, R> SelectBuilder select(ColumnLambda<T, R>... functions) {
    return new SelectBuilder(new NormalColumnsBuilder(asList(functions)));
  }

  public <T, R, O> SelectBuilder selectAs(FunctionType<T, R> columnBuilder,
      ColumnLambda<O, R> alias) {
    return new SelectBuilder(new FunctionColumnBuilder(columnBuilder, alias));
  }
}
