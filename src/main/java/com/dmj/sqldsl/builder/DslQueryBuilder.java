package com.dmj.sqldsl.builder;

import static java.util.Arrays.asList;

import com.dmj.sqldsl.builder.column.ColumnFunction;
import com.dmj.sqldsl.builder.column.EntityColumnsBuilder;
import com.dmj.sqldsl.builder.column.FunctionColumnsBuilder;

public class DslQueryBuilder {

  @SafeVarargs
  public static <T, R> SelectBuilder selectAll(Class<T> entityClass,
      ColumnFunction<T, R>... excludeColumns) {
    return new SelectBuilder(new EntityColumnsBuilder(entityClass, asList(excludeColumns)));
  }

  @SafeVarargs
  public static <T, R> SelectBuilder select(ColumnFunction<T, R>... functions) {
    return new SelectBuilder(new FunctionColumnsBuilder(asList(functions)));
  }
}
