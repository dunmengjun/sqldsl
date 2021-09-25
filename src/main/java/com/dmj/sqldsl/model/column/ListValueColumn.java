package com.dmj.sqldsl.model.column;

import java.util.Collection;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ListValueColumn implements Column {

  private Collection<?> list;

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
