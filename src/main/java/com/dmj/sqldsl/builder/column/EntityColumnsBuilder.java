package com.dmj.sqldsl.builder.column;

import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.table.TableBuilder;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.table.Table;
import java.util.List;

public class EntityColumnsBuilder implements ColumnsBuilder {

  private final TableBuilder tableBuilder;
  private final List<ColumnBuilder<?, ?>> columnBuilders;

  public EntityColumnsBuilder(TableBuilder tableBuilder,
      List<ColumnBuilder<?, ?>> columnBuilders) {
    this.tableBuilder = tableBuilder;
    this.columnBuilders = columnBuilders;
  }

  @Override
  public List<Column> build(EntityConfig config) {
    Table table = tableBuilder.build(config);
    List<Column> columns = table.getColumns();
    List<Column> excludeColumns = columnBuilders.stream()
        .map(columnBuilder -> columnBuilder.build(config))
        .collect(toList());
    columns.removeAll(excludeColumns);
    return columns;
  }
}
