package com.dmj.sqldsl.builder;

import static java.util.Arrays.asList;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.EntityColumnsBuilder;
import com.dmj.sqldsl.builder.column.FunctionColumnBuilder;
import com.dmj.sqldsl.builder.column.LambdaAliasColumnBuilder;
import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.FunctionType;
import com.dmj.sqldsl.builder.column.type.SerializableLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.config.GlobalConfig;
import com.dmj.sqldsl.builder.exception.GlobalConfigNotValidException;
import com.dmj.sqldsl.builder.table.EntityBuilder;
import com.dmj.sqldsl.builder.table.TableBuilder;
import com.dmj.sqldsl.model.DslQuery;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    List<ColumnBuilder<?, ?>> columnBuilders = Arrays.stream(excludeColumns)
        .map(SerializableLambda::getColumnBuilder)
        .collect(Collectors.toList());
    return new SelectBuilder(
        new EntityColumnsBuilder(new EntityBuilder(entityClass), columnBuilders));
  }

  @SafeVarargs
  static <T, R> SelectBuilder selectAll(TableBuilder tableBuilder,
      ColumnBuilder<T, R>... columnBuilders) {
    return new SelectBuilder(new EntityColumnsBuilder(tableBuilder, asList(columnBuilders)));
  }

  @SafeVarargs
  static <T, R> SelectBuilder select(ColumnLambda<T, R>... functions) {
    List<ColumnBuilder<?, ?>> columnBuilders = Arrays.stream(functions)
        .map(SerializableLambda::getColumnBuilder).collect(Collectors.toList());
    return new SelectBuilder(new NormalColumnsBuilder(columnBuilders));
  }

  @SafeVarargs
  static <T, R> SelectBuilder select(ColumnBuilder<T, R>... columnBuilders) {
    return new SelectBuilder(new NormalColumnsBuilder(asList(columnBuilders)));
  }

  static <T, R, O> SelectBuilder selectAs(FunctionType<T, R> columnBuilder,
      ColumnLambda<O, R> alias) {
    return new SelectBuilder(new FunctionColumnBuilder<T, R>(columnBuilder, alias));
  }

  static <T, R, O> SelectBuilder selectAs(ColumnLambda<T, R> column,
      ColumnLambda<O, R> alias) {
    return new SelectBuilder(new LambdaAliasColumnBuilder<>(column, alias));
  }
}
