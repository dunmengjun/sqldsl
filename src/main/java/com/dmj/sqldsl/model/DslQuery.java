package com.dmj.sqldsl.model;

import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Builder
public class DslQuery {

  @Getter
  private SelectFrom selectFrom;
  private Where where;
  private Limit limit;
  private GroupBy groupBy;
  private Having having;
  private OrderBy orderBy;

  public Optional<Limit> getLimit() {
    return Optional.ofNullable(limit);
  }

  public Optional<Where> getWhere() {
    if (where == null || where.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(where);
  }

  public Optional<Having> getHaving() {
    if (having == null || having.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(having);
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
