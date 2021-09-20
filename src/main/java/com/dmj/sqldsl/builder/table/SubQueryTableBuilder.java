package com.dmj.sqldsl.builder.table;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.LambdaColumnBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.table.SubQueryTable;
import com.dmj.sqldsl.model.table.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubQueryTableBuilder implements TableBuilder {

  private DslQueryBuilder queryBuilder;

  private String alias;

  public static SubQueryTableBuilder ref(DslQueryBuilder queryBuilder) {
    return new SubQueryTableBuilder(queryBuilder, TableBuilder.getAlias("subQuery"));
  }

  public <T, R> ColumnBuilder col(ColumnLambda<T, R> lambda) {
    return new LambdaColumnBuilder(lambda, alias);
  }

  @Override
  public Table build(EntityConfig config) {
    DslQuery dslQuery = queryBuilder.toQuery(config);
    return new SubQueryTable(dslQuery, alias);
  }
}
