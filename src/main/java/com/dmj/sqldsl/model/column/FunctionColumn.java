package com.dmj.sqldsl.model.column;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class FunctionColumn implements Column {

  private Function function;
  private List<Column> params;

  @Override
  public Optional<String> getTableName() {
    return Optional.empty();
  }

  @Override
  public String getFieldName() {
    return "";
  }

  @Override
  public String getColumnName() {
    return "";
  }
}
