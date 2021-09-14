package com.dmj.sqldsl.model.column;

import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;

@EqualsAndHashCode
@Builder
public class SimpleColumn implements Column {

  private final String tableName;

  @Getter
  private final String name;

  @Exclude
  private String alias;

  @Override
  public Optional<String> getAlias() {
    if (alias == null) {
      return Optional.empty();
    }
    return Optional.of(alias);
  }

  public Optional<String> getTableName() {
    if (tableName == null) {
      return Optional.empty();
    }
    return Optional.of(tableName);
  }
}
