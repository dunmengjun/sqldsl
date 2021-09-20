package com.dmj.sqldsl.builder;

import static java.util.Arrays.asList;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.ColumnsBuilder;
import com.dmj.sqldsl.builder.column.EntityColumnsBuilder;
import com.dmj.sqldsl.builder.column.FunctionColumnBuilder;
import com.dmj.sqldsl.builder.column.LambdaAliasColumnBuilder;
import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.FunctionType;
import com.dmj.sqldsl.builder.column.type.SerializableLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.table.EntityTableBuilder;
import com.dmj.sqldsl.model.column.Column;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SelectBuilder {

  private final List<ColumnsBuilder> columnsBuilders;
  private final List<ColumnBuilder> aliasBuilders;

  public SelectBuilder(ColumnsBuilder columnsBuilder) {
    this.columnsBuilders = new ArrayList<>();
    this.columnsBuilders.add(columnsBuilder);
    this.aliasBuilders = new ArrayList<>();
  }

  public SelectBuilder(ColumnBuilder aliasBuilder) {
    this.columnsBuilders = new ArrayList<>();
    this.aliasBuilders = new ArrayList<>();
    this.aliasBuilders.add(aliasBuilder);
  }

  @SafeVarargs
  public final <T, R> SelectBuilder selectAll(Class<T> entityClass,
      ColumnLambda<T, R>... excludeColumns) {
    List<ColumnBuilder> columnBuilders = Arrays.stream(excludeColumns)
        .map(SerializableLambda::getColumnBuilder)
        .collect(Collectors.toList());
    this.columnsBuilders.add(
        new EntityColumnsBuilder(new EntityTableBuilder(entityClass), columnBuilders));
    return this;
  }

  public SelectBuilder selectAll(EntityTableBuilder tableBuilder,
      ColumnBuilder... columnBuilders) {
    this.columnsBuilders.add(new EntityColumnsBuilder(tableBuilder, asList(columnBuilders)));
    return this;
  }

  @SafeVarargs
  public final <T, R> SelectBuilder select(ColumnLambda<T, R>... functions) {
    this.columnsBuilders.add(new NormalColumnsBuilder(asList(functions)));
    return this;
  }

  public <T, R, O, K> SelectBuilder selectAs(ColumnLambda<T, R> column,
      ColumnLambda<O, K> alias) {
    this.aliasBuilders.add(new LambdaAliasColumnBuilder(column, alias));
    return this;
  }

  public <T, R, O> SelectBuilder selectAs(FunctionType<T, R> functionType,
      ColumnLambda<O, R> alias) {
    this.aliasBuilders.add(new FunctionColumnBuilder(functionType, alias));
    return this;
  }

  public FromBuilder from(Class<?> entityClass) {
    return new FromBuilder(this, new EntityTableBuilder(entityClass));
  }

  protected List<Column> build(EntityConfig config) {
    List<Column> columns = this.columnsBuilders.stream()
        .flatMap(columnsBuilder -> columnsBuilder.build(config).stream())
        .distinct()
        .collect(Collectors.toList());
    this.aliasBuilders.stream()
        .map(columnBuilder -> columnBuilder.build(config))
        .distinct()
        .forEach(aliasColumn -> {
          int index = columns.indexOf(aliasColumn);
          if (index >= 0) {
            columns.set(index, aliasColumn);
          } else {
            columns.add(aliasColumn);
          }
        });
    return columns;
  }
}
