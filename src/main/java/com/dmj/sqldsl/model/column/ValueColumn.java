package com.dmj.sqldsl.model.column;

import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ValueColumn implements Column {

  @Exclude
  private final Object value;
  private boolean isNullValue;
  private String fieldName;
  private String columnName;

  public ValueColumn(Object value, String fieldName, String columnName) {
    this.value = value;
    this.isNullValue = value == null;
    this.fieldName = fieldName;
    this.columnName = columnName;
  }

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
