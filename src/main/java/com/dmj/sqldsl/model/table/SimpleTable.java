package com.dmj.sqldsl.model.table;

import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

public class SimpleTable implements Table {

  private String alias;

  @Getter
  private final String tableName;

  private final List<Column> columns;

  public SimpleTable(List<Column> columns, String tableName) {
    this.columns = columns;
    this.tableName = tableName;
  }

  public SimpleTable(List<Column> columns, String tableName, String alias) {
    this.columns = columns;
    this.alias = alias;
    this.tableName = tableName;
  }

  public Optional<String> getAlias() {
    return Optional.ofNullable(alias);
  }

  @Override
  public String getName() {
    return getAlias().orElse(tableName);
  }

  @Override
  public List<Column> getColumns() {
    return columns;
  }
}
