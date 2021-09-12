package com.dmj.sqldsl.model.column;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValueColumn implements Column {

  private Object value;

  @Override
  public String getTableName() {
    return "";
  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public Optional<String> getAlias() {
    return Optional.empty();
  }
}
