package com.dmj.sqldsl.service;

import static java.util.Collections.emptyList;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.GroupByBuilder;
import com.dmj.sqldsl.builder.OrderByBuilder;
import com.dmj.sqldsl.builder.SelectBuilder;
import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.DateRange;
import com.dmj.sqldsl.builder.column.LikeValue;
import com.dmj.sqldsl.builder.column.type.BooleanLambda;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.DateLambda;
import com.dmj.sqldsl.builder.column.type.LongLambda;
import com.dmj.sqldsl.builder.column.type.NumberLambda;
import com.dmj.sqldsl.builder.column.type.StringLambda;
import com.dmj.sqldsl.builder.condition.ConditionBuilders;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Wrapper<T> {

  private final ConditionsBuilder conditionsBuilder;
  private final SelectBuilder selectBuilder;
  private final List<OrderByCondition<T, ?>> orderByConditions;
  private List<ColumnLambda<T, ?>> groupByLambdas;
  private Limit limit;
  private ConditionsBuilder havingConditionsBuilder;
  private final Class<T> entityClass;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static class OrderByCondition<T, R> {

    private ColumnLambda<T, R> lambda;
    private boolean isAsc;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static class Limit {

    private int offset;
    private int size;
  }

  public Wrapper(Class<T> targetClass) {
    this(DslQueryBuilder.selectAll(targetClass), targetClass);
  }

  protected Wrapper(SelectBuilder selectBuilder, Class<T> targetClass) {
    this.entityClass = targetClass;
    this.conditionsBuilder = ConditionBuilders.empty();
    this.selectBuilder = selectBuilder;
    this.orderByConditions = new ArrayList<>();
    this.groupByLambdas = emptyList();
    this.havingConditionsBuilder = ConditionBuilders.empty();
  }

  public Wrapper<T> eq(StringLambda<T> lambda, String value) {
    this.conditionsBuilder.eq(lambda, value);
    return this;
  }

  public Wrapper<T> eq(NumberLambda<T> lambda, Number value) {
    this.conditionsBuilder.eq(lambda, value);
    return this;
  }

  public Wrapper<T> eq(BooleanLambda<T> lambda, Boolean value) {
    this.conditionsBuilder.eq(lambda, value);
    return this;
  }

  public Wrapper<T> eq(ColumnBuilder<T, Boolean> columnBuilder, Boolean value) {
    this.conditionsBuilder.eq(columnBuilder, value);
    return this;
  }

  public Wrapper<T> eq(ColumnBuilder<T, String> columnBuilder, String value) {
    this.conditionsBuilder.eq(columnBuilder, value);
    return this;
  }

  public Wrapper<T> eq(ColumnBuilder<T, Number> columnBuilder, Number value) {
    this.conditionsBuilder.eq(columnBuilder, value);
    return this;
  }

  public Wrapper<T> ne(StringLambda<T> lambda, String value) {
    this.conditionsBuilder.ne(lambda, value);
    return this;
  }

  public Wrapper<T> ne(NumberLambda<T> lambda, Number value) {
    this.conditionsBuilder.ne(lambda, value);
    return this;
  }

  public Wrapper<T> ne(BooleanLambda<T> lambda, Boolean value) {
    this.conditionsBuilder.ne(lambda, value);
    return this;
  }

  public Wrapper<T> ne(ColumnBuilder<T, Boolean> columnBuilder, Boolean value) {
    this.conditionsBuilder.ne(columnBuilder, value);
    return this;
  }

  public Wrapper<T> ne(ColumnBuilder<T, String> columnBuilder, String value) {
    this.conditionsBuilder.ne(columnBuilder, value);
    return this;
  }

  public Wrapper<T> ne(ColumnBuilder<T, Number> columnBuilder, Number value) {
    this.conditionsBuilder.ne(columnBuilder, value);
    return this;
  }

  public Wrapper<T> gt(StringLambda<T> lambda, String value) {
    this.conditionsBuilder.gt(lambda, value);
    return this;
  }

  public Wrapper<T> gt(NumberLambda<T> lambda, Number value) {
    this.conditionsBuilder.gt(lambda, value);
    return this;
  }

  public Wrapper<T> gt(BooleanLambda<T> lambda, Boolean value) {
    this.conditionsBuilder.gt(lambda, value);
    return this;
  }

  public Wrapper<T> gt(ColumnBuilder<T, Boolean> columnBuilder, Boolean value) {
    this.conditionsBuilder.ne(columnBuilder, value);
    return this;
  }

  public Wrapper<T> gt(ColumnBuilder<T, String> columnBuilder, String value) {
    this.conditionsBuilder.ne(columnBuilder, value);
    return this;
  }

  public Wrapper<T> gt(ColumnBuilder<T, Number> columnBuilder, Number value) {
    this.conditionsBuilder.ne(columnBuilder, value);
    return this;
  }

  public Wrapper<T> ge(StringLambda<T> lambda, String value) {
    this.conditionsBuilder.ge(lambda, value);
    return this;
  }

  public Wrapper<T> ge(NumberLambda<T> lambda, Number value) {
    this.conditionsBuilder.ge(lambda, value);
    return this;
  }

  public Wrapper<T> ge(BooleanLambda<T> lambda, Boolean value) {
    this.conditionsBuilder.ge(lambda, value);
    return this;
  }

  public Wrapper<T> ge(ColumnBuilder<T, Boolean> columnBuilder, Boolean value) {
    this.conditionsBuilder.ge(columnBuilder, value);
    return this;
  }

  public Wrapper<T> ge(ColumnBuilder<T, String> columnBuilder, String value) {
    this.conditionsBuilder.ge(columnBuilder, value);
    return this;
  }

  public Wrapper<T> ge(ColumnBuilder<T, Number> columnBuilder, Number value) {
    this.conditionsBuilder.ne(columnBuilder, value);
    return this;
  }

  public Wrapper<T> lt(StringLambda<T> lambda, String value) {
    this.conditionsBuilder.lt(lambda, value);
    return this;
  }

  public Wrapper<T> lt(NumberLambda<T> lambda, Number value) {
    this.conditionsBuilder.lt(lambda, value);
    return this;
  }

  public Wrapper<T> lt(BooleanLambda<T> lambda, Boolean value) {
    this.conditionsBuilder.lt(lambda, value);
    return this;
  }

  public Wrapper<T> lt(ColumnBuilder<T, Boolean> columnBuilder, Boolean value) {
    this.conditionsBuilder.lt(columnBuilder, value);
    return this;
  }

  public Wrapper<T> lt(ColumnBuilder<T, String> columnBuilder, String value) {
    this.conditionsBuilder.lt(columnBuilder, value);
    return this;
  }

  public Wrapper<T> lt(ColumnBuilder<T, Number> columnBuilder, Number value) {
    this.conditionsBuilder.lt(columnBuilder, value);
    return this;
  }

  public Wrapper<T> le(StringLambda<T> lambda, String value) {
    this.conditionsBuilder.le(lambda, value);
    return this;
  }

  public Wrapper<T> le(NumberLambda<T> lambda, Number value) {
    this.conditionsBuilder.le(lambda, value);
    return this;
  }

  public Wrapper<T> le(BooleanLambda<T> lambda, Boolean value) {
    this.conditionsBuilder.le(lambda, value);
    return this;
  }

  public Wrapper<T> le(ColumnBuilder<T, Boolean> columnBuilder, Boolean value) {
    this.conditionsBuilder.le(columnBuilder, value);
    return this;
  }

  public Wrapper<T> le(ColumnBuilder<T, String> columnBuilder, String value) {
    this.conditionsBuilder.le(columnBuilder, value);
    return this;
  }

  public Wrapper<T> le(ColumnBuilder<T, Number> columnBuilder, Number value) {
    this.conditionsBuilder.le(columnBuilder, value);
    return this;
  }

  public Wrapper<T> in(NumberLambda<T> lambda, Collection<Number> valueList) {
    this.conditionsBuilder.in(lambda, valueList);
    return this;
  }

  public Wrapper<T> in(StringLambda<T> lambda, Collection<String> valueList) {
    this.conditionsBuilder.in(lambda, valueList);
    return this;
  }

  public Wrapper<T> in(NumberLambda<T> lambda, Number... valueList) {
    this.conditionsBuilder.in(lambda, valueList);
    return this;
  }

  public Wrapper<T> in(StringLambda<T> lambda, String... valueList) {
    this.conditionsBuilder.in(lambda, valueList);
    return this;
  }

  public Wrapper<T> notIn(NumberLambda<T> lambda, Collection<Number> valueList) {
    this.conditionsBuilder.notIn(lambda, valueList);
    return this;
  }

  public Wrapper<T> notIn(StringLambda<T> lambda, Collection<String> valueList) {
    this.conditionsBuilder.notIn(lambda, valueList);
    return this;
  }

  public Wrapper<T> notIn(NumberLambda<T> lambda, Number... valueList) {
    this.conditionsBuilder.notIn(lambda, valueList);
    return this;
  }

  public Wrapper<T> notIn(StringLambda<T> lambda, String... valueList) {
    this.conditionsBuilder.notIn(lambda, valueList);
    return this;
  }

  public Wrapper<T> like(StringLambda<T> lambda, LikeValue likeValue) {
    this.conditionsBuilder.like(lambda, likeValue);
    return this;
  }

  public Wrapper<T> notLike(StringLambda<T> lambda, LikeValue likeValue) {
    this.conditionsBuilder.notLike(lambda, likeValue);
    return this;
  }

  public Wrapper<T> like(ColumnBuilder<T, String> lambda, LikeValue likeValue) {
    this.conditionsBuilder.like(lambda, likeValue);
    return this;
  }

  public Wrapper<T> notLike(ColumnBuilder<T, String> lambda, LikeValue likeValue) {
    this.conditionsBuilder.notLike(lambda, likeValue);
    return this;
  }

  public Wrapper<T> between(DateLambda<T> lambda, DateRange range) {
    this.conditionsBuilder.between(lambda, range);
    return this;
  }

  public Wrapper<T> and() {
    this.conditionsBuilder.and();
    return this;
  }

  public Wrapper<T> and(Consumer<Wrapper<T>> consumer) {
    Wrapper<T> wrapper = new Wrapper<>(selectBuilder, entityClass);
    consumer.accept(wrapper);
    this.conditionsBuilder.and(wrapper.getConditionsBuilder());
    return this;
  }

  public Wrapper<T> or() {
    this.conditionsBuilder.or();
    return this;
  }

  public Wrapper<T> or(Consumer<Wrapper<T>> consumer) {
    Wrapper<T> wrapper = new Wrapper<>(selectBuilder, entityClass);
    consumer.accept(wrapper);
    this.conditionsBuilder.or(wrapper.getConditionsBuilder());
    return this;
  }

  public final <R> Wrapper<T> orderBy(ColumnLambda<T, R> lambda, boolean isAsc) {
    this.orderByConditions.add(new OrderByCondition<>(lambda, isAsc));
    return this;
  }

  @SafeVarargs
  public final <R> Wrapper<T> groupBy(ColumnLambda<T, R>... lambdas) {
    this.groupByLambdas = Arrays.asList(lambdas);
    return this;
  }

  public Wrapper<T> having(Consumer<Wrapper<T>> wrapperConsumer) {
    Wrapper<T> wrapper = new Wrapper<>(entityClass);
    wrapperConsumer.accept(wrapper);
    this.havingConditionsBuilder = wrapper.getConditionsBuilder();
    return this;
  }

  public Wrapper<T> selectAs(ColumnBuilder<T, String> columnBuilder, StringLambda<T> alias) {
    this.selectBuilder.selectAs(columnBuilder, alias);
    return this;
  }

  public Wrapper<T> selectAs(ColumnBuilder<T, Long> columnBuilder, LongLambda<T> alias) {
    this.selectBuilder.selectAs(columnBuilder, alias);
    return this;
  }

  public Wrapper<T> selectAs(ColumnBuilder<T, Number> columnBuilder, NumberLambda<T> alias) {
    this.selectBuilder.selectAs(columnBuilder, alias);
    return this;
  }

  public Wrapper<T> selectAs(ColumnBuilder<T, Boolean> columnBuilder, BooleanLambda<T> alias) {
    this.selectBuilder.selectAs(columnBuilder, alias);
    return this;
  }

  public Wrapper<T> limit(int size) {
    this.limit = new Limit(0, size);
    return this;
  }

  private ConditionsBuilder getConditionsBuilder() {
    return this.conditionsBuilder;
  }

  protected Class<T> getEntityClass() {
    return this.entityClass;
  }

  protected DslQueryBuilder toQueryBuilder() {
    GroupByBuilder having = this.selectBuilder
        .from(entityClass)
        .where(conditionsBuilder)
        .groupBy(groupByLambdas)
        .having(havingConditionsBuilder);
    DslQueryBuilder queryBuilder = having;
    if (!orderByConditions.isEmpty()) {
      OrderByCondition<T, ?> order = orderByConditions.remove(0);
      OrderByBuilder orderByBuilder = having.orderBy(order.getLambda(), order.isAsc());
      if (!orderByConditions.isEmpty()) {
        orderByConditions.forEach(c -> orderByBuilder.orderBy(c.getLambda(), c.isAsc()));
      }
      queryBuilder = orderByBuilder;
    }
    if (this.limit != null) {
      queryBuilder.limit(limit.getOffset(), limit.getSize());
    }
    return queryBuilder;
  }
}
