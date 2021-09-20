package com.dmj.sqldsl.model.column;

import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Builder
public class SimpleColumn implements Column {

  private final String tableName;

  @Getter
  private final String name;

  public Optional<String> getTableName() {
    return Optional.ofNullable(tableName);
  }

  @Override
  public String getRealName() {
    return getName();
  }
}
