package com.dmj.sqldsl.model.table;

import java.util.Optional;
import lombok.Getter;

public class SimpleTable implements Table {

  private String alias;

  @Getter
  private final String tableName;

  public SimpleTable(String tableName) {
    this.tableName = tableName;
  }

  public SimpleTable(String tableName, String alias) {
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
}
