package com.dmj.sqldsl.model.column;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValueColumn implements Column {

  private Object value;

  @Override
  public Optional<String> getTableName() {
    return Optional.empty();
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
