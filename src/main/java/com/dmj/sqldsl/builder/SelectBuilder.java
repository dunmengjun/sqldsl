package com.dmj.sqldsl.builder;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.ColumnFunction;
import com.dmj.sqldsl.builder.column.ColumnsBuilder;
import com.dmj.sqldsl.builder.column.EntityColumnsBuilder;
import com.dmj.sqldsl.builder.column.FunctionAliasColumnBuilder;
import com.dmj.sqldsl.builder.column.FunctionColumnsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.table.EntityTablesBuilder;
import com.dmj.sqldsl.model.column.Column;
import java.util.ArrayList;
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

  @SafeVarargs
  public final <T, R> SelectBuilder selectAll(Class<T> entityClass,
      ColumnFunction<T, R>... excludeColumns) {
    this.columnsBuilders.add(new EntityColumnsBuilder(entityClass, asList(excludeColumns)));
    return this;
  }

  @SafeVarargs
  public final <T, R> SelectBuilder select(ColumnFunction<T, R>... functions) {
    this.columnsBuilders.add(new FunctionColumnsBuilder(asList(functions)));
    return this;
  }

  public <T, R, O, K> SelectBuilder selectAs(ColumnFunction<T, R> column,
      ColumnFunction<O, K> alias) {
    this.aliasBuilders.add(new FunctionAliasColumnBuilder(column, alias));
    return this;
  }

  public FromBuilder from(Class<?> entityClasses) {
    return new FromBuilder(this, new EntityTablesBuilder(singletonList(entityClasses)));
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
          columns.set(index, aliasColumn);
        });
    return columns;
  }
}
