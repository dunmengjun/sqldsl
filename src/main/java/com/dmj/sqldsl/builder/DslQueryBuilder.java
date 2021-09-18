package com.dmj.sqldsl.builder;

import static java.util.Arrays.asList;

import com.dmj.sqldsl.builder.column.EntityColumnsBuilder;
import com.dmj.sqldsl.builder.column.FunctionColumnBuilder;
import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.FunctionType;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.config.GlobalConfig;
import com.dmj.sqldsl.builder.exception.GlobalConfigNotValidException;
import com.dmj.sqldsl.model.DslQuery;

public interface DslQueryBuilder {

  DslQuery toQuery(EntityConfig config);

  default DslQuery toQuery() {
    if (!GlobalConfig.isValid()) {
      throw new GlobalConfigNotValidException();
    }
    return toQuery(GlobalConfig.entityConfig);
  }

  @SafeVarargs
  static <T, R> SelectBuilder selectAll(Class<T> entityClass,
      ColumnLambda<T, R>... excludeColumns) {
    return new SelectBuilder(new EntityColumnsBuilder(entityClass, asList(excludeColumns)));
  }

  @SafeVarargs
  static <T, R> SelectBuilder select(ColumnLambda<T, R>... functions) {
    return new SelectBuilder(new NormalColumnsBuilder(asList(functions)));
  }

  static <T, R, O> SelectBuilder selectAs(FunctionType<T, R> columnBuilder,
      ColumnLambda<O, R> alias) {
    return new SelectBuilder(new FunctionColumnBuilder(columnBuilder, alias));
  }
}
