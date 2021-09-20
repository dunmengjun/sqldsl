package com.dmj.sqldsl.builder.table;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.LambdaColumnBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.model.table.SubQueryTable;
import com.dmj.sqldsl.model.table.Table;
import java.util.List;
import java.util.stream.Collectors;

public class SubQueryTableBuilder implements TableBuilder {

  private final DslQueryBuilder queryBuilder;

  private final String alias;

  private DslQuery query;

  private SubQueryTableBuilder(DslQueryBuilder queryBuilder, String alias) {
    this.queryBuilder = queryBuilder;
    this.alias = alias;
  }

  public static SubQueryTableBuilder ref(DslQueryBuilder queryBuilder) {
    return new SubQueryTableBuilder(queryBuilder, TableBuilder.getAlias("subQuery"));
  }

  public <T, R> ColumnBuilder<T, R> col(ColumnLambda<T, R> lambda) {
    return new LambdaColumnBuilder<>(lambda, alias);
  }

  @Override
  public Table buildTable(EntityConfig config) {
    if (query == null) {
      this.query = queryBuilder.toQuery(config);
    }
    return new SubQueryTable(query, alias);
  }

  @Override
  public List<Column> buildColumns(EntityConfig config) {
    if (query == null) {
      this.query = queryBuilder.toQuery(config);
    }
    return query.getSelectFrom().getColumns().stream()
        .map(column -> SimpleColumn.builder().tableName(alias).name(column.getName()).build())
        .collect(Collectors.toList());
  }
}
