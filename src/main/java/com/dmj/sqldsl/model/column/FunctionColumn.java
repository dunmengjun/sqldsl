package com.dmj.sqldsl.model.column;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class FunctionColumn implements Column {

  @Getter
  private Function function;
  private Column column;

  @Override
  public Optional<String> getTableName() {
    return column.getTableName();
  }

  @Override
  public String getName() {
    return column.getName();
  }

  @Override
  public Optional<String> getAlias() {
    return column.getAlias();
  }
}
