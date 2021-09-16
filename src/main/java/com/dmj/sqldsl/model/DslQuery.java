package com.dmj.sqldsl.model;

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
  private GroupBy groupBy;
  private OrderBy orderBy;

  public Optional<Limit> getLimit() {
    return Optional.ofNullable(limit);
  }

  public Optional<Conditions> getConditions() {
    return Optional.ofNullable(conditions);
  }

  public Optional<GroupBy> getGroupBy() {
    return Optional.ofNullable(groupBy);
  }

  public Optional<OrderBy> getOrderBy() {
    return Optional.ofNullable(orderBy);
  }
}
