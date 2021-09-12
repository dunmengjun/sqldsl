package com.dmj.sqldsl.model.column;

import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;

@EqualsAndHashCode
public class SimpleColumn implements Column {

  @Getter
  private final String tableName;

  @Getter
  private final String name;

  @Exclude
  private String alias;

  public SimpleColumn(String tableName, String name) {
    this.tableName = tableName;
    this.name = name;
  }

  public SimpleColumn(String tableName, String name, String alias) {
    this.tableName = tableName;
    this.name = name;
    this.alias = alias;
  }

  @Override
  public Optional<String> getAlias() {
    if (alias == null) {
      return Optional.empty();
    }
    return Optional.of(alias);
  }
}
