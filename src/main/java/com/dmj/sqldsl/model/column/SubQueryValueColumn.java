package com.dmj.sqldsl.model.column;

import com.dmj.sqldsl.model.DslQuery;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SubQueryValueColumn implements Column {

  @Getter
  private DslQuery query;

  @Override
  public Optional<String> getTableName() {
    return Optional.empty();
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public Optional<String> getAlias() {
    return Optional.empty();
  }
}
