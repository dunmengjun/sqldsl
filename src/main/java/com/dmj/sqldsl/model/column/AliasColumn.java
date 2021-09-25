package com.dmj.sqldsl.model.column;

import java.util.Optional;
import lombok.Getter;

@Getter
public class AliasColumn implements Column {

  private final Column column;

  private final String alias;

  public AliasColumn(Column column, String alias) {
    this.column = column;
    this.alias = alias;
  }

  @Override
  public Optional<String> getTableName() {
    return column.getTableName();
  }

  @Override
  public String getFieldName() {
    return getAlias();
  }

  @Override
  public String getColumnName() {
    return getAlias();
  }
}
