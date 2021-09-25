package com.dmj.sqldsl.model.column;

import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Builder
public class SimpleColumn implements Column {

  private final String tableName;

  private String fieldName;

  @Getter
  private final String columnName;

  public Optional<String> getTableName() {
    return Optional.ofNullable(tableName);
  }

  @Override
  public String getFieldName() {
    return Optional.ofNullable(fieldName).orElse(columnName);
  }
}
