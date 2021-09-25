package com.dmj.sqldsl.model.column;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValueColumn implements Column {

  private Object value;
  private String fieldName;
  private String columnName;

  public ValueColumn(Object value) {
    this.value = value;
  }

  @Override
  public Optional<String> getTableName() {
    return Optional.empty();
  }

  @Override
  public String getFieldName() {
    return fieldName;
  }

  @Override
  public String getColumnName() {
    return columnName;
  }
}
