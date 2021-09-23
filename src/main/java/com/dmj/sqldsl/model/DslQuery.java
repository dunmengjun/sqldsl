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
    if (conditions == null || conditions.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(conditions);
  }

  public Optional<GroupBy> getGroupBy() {
    if (groupBy == null || groupBy.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(groupBy);
  }

  public Optional<OrderBy> getOrderBy() {
    if (orderBy == null || orderBy.isEmpty()) {
      return Optional.empty();
    }
    return Optional.ofNullable(orderBy);
  }
}
