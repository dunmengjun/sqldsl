package com.dmj.sqldsl.model;

import com.dmj.sqldsl.builder.Limit;
import com.dmj.sqldsl.model.condition.Conditions;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Builder
public class DslQuery {

  @Getter
  private SelectFrom selectFrom;
  private Conditions conditions;
  private Limit limit;

  public Optional<Limit> getLimit() {
    return Optional.ofNullable(limit);
  }

  public Optional<Conditions> getConditions() {
    return Optional.ofNullable(conditions);
  }
}
