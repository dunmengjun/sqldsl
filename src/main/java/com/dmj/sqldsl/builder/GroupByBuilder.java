package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.ColumnFunction;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import java.util.Arrays;
import java.util.List;

public class GroupByBuilder implements ToDslQuery {

  private final FromBuilder fromBuilder;
  private final List<ColumnFunction<?, ?>> functions;

  public GroupByBuilder(FromBuilder fromBuilder, List<ColumnFunction<?, ?>> functions) {
    this.fromBuilder = fromBuilder;
    this.functions = functions;
  }

  @SafeVarargs
  public final <T, R> GroupByBuilder groupBy(ColumnFunction<T, R>... functions) {
    this.functions.addAll(Arrays.asList(functions));
    return this;
  }

  @Override
  public DslQuery toQuery(EntityConfig config) {
    return null;
  }
}
