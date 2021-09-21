package com.dmj.sqldsl.builder;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.AliasColumnBuilder;
import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.ColumnsBuilder;
import com.dmj.sqldsl.builder.column.EntityColumnsBuilder;
import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.SerializableLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.table.EntityBuilder;
import com.dmj.sqldsl.builder.table.TableBuilder;
import com.dmj.sqldsl.model.column.Column;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectBuilder {

  private final List<ColumnsBuilder> columnsBuilders;
  private final List<ColumnBuilder<?, ?>> aliasBuilders;

  public SelectBuilder(ColumnsBuilder columnsBuilder) {
    this.columnsBuilders = new ArrayList<>();
    this.columnsBuilders.add(columnsBuilder);
    this.aliasBuilders = new ArrayList<>();
  }

  public SelectBuilder(ColumnBuilder<?, ?> aliasBuilder) {
    this.columnsBuilders = new ArrayList<>();
    this.aliasBuilders = new ArrayList<>();
    this.aliasBuilders.add(aliasBuilder);
  }

  @SafeVarargs
  public final <T, R> SelectBuilder selectAll(Class<T> entityClass,
      ColumnLambda<T, R>... excludeColumns) {
    List<ColumnBuilder<?, ?>> columnBuilders = Arrays.stream(excludeColumns)
        .map(SerializableLambda::getColumnBuilder)
        .collect(toList());
    this.columnsBuilders.add(
        new EntityColumnsBuilder(new EntityBuilder(entityClass), columnBuilders));
    return this;
  }

  @SafeVarargs
  public final <T, R> SelectBuilder selectAll(TableBuilder tableBuilder,
      ColumnBuilder<T, R>... columnBuilders) {
    this.columnsBuilders.add(new EntityColumnsBuilder(tableBuilder, asList(columnBuilders)));
    return this;
  }

  @SafeVarargs
  public final <T, R> SelectBuilder select(ColumnLambda<T, R>... functions) {
    List<ColumnBuilder<?, ?>> columnBuilders = Arrays.stream(functions)
        .map(SerializableLambda::getColumnBuilder).collect(toList());
    this.columnsBuilders.add(new NormalColumnsBuilder(columnBuilders));
    return this;
  }

  @SafeVarargs
  public final <T, R> SelectBuilder select(ColumnBuilder<T, R>... columnBuilders) {
    this.columnsBuilders.add(new NormalColumnsBuilder(asList(columnBuilders)));
    return this;
  }

  public <T, R, O> SelectBuilder selectAs(ColumnLambda<T, R> column,
      ColumnLambda<O, R> alias) {
    this.aliasBuilders.add(new AliasColumnBuilder<>(column.getColumnBuilder(), alias));
    return this;
  }

  public <T, R, O> SelectBuilder selectAs(ColumnBuilder<T, R> columnBuilder,
      ColumnLambda<O, R> alias) {
    this.aliasBuilders.add(new AliasColumnBuilder<>(columnBuilder, alias));
    return this;
  }

  public FromBuilder from(Class<?> entityClass) {
    return new FromBuilder(this, new EntityBuilder(entityClass));
  }

  public FromBuilder from(TableBuilder tableBuilder) {
    return new FromBuilder(this, tableBuilder);
  }

  protected List<Column> build(EntityConfig config) {
    List<Column> columns = this.columnsBuilders.stream()
        .flatMap(columnsBuilder -> columnsBuilder.build(config).stream())
        .distinct()
        .collect(toList());
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
