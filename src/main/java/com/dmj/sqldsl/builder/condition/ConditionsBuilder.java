package com.dmj.sqldsl.builder.condition;

import static com.dmj.sqldsl.model.condition.ConditionMethod.eq;
import static com.dmj.sqldsl.model.condition.ConditionMethod.ge;
import static com.dmj.sqldsl.model.condition.ConditionMethod.gt;
import static com.dmj.sqldsl.model.condition.ConditionMethod.in;
import static com.dmj.sqldsl.model.condition.ConditionMethod.le;
import static com.dmj.sqldsl.model.condition.ConditionMethod.like;
import static com.dmj.sqldsl.model.condition.ConditionMethod.lt;
import static com.dmj.sqldsl.model.condition.ConditionMethod.ne;
import static com.dmj.sqldsl.model.condition.ConditionMethod.notIn;
import static com.dmj.sqldsl.model.condition.ConditionMethod.notLike;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.DateRange;
import com.dmj.sqldsl.builder.column.LikeValue;
import com.dmj.sqldsl.builder.column.ValueColumnBuilder;
import com.dmj.sqldsl.builder.column.type.BooleanLambda;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.DateLambda;
import com.dmj.sqldsl.builder.column.type.LongLambda;
import com.dmj.sqldsl.builder.column.type.NumberLambda;
import com.dmj.sqldsl.builder.column.type.SerializableLambda;
import com.dmj.sqldsl.builder.column.type.StringLambda;
import com.dmj.sqldsl.builder.column.type.SubQueryType;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.condition.ConditionElement;
import com.dmj.sqldsl.model.condition.ConditionMethod;
import com.dmj.sqldsl.model.condition.Conditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ConditionsBuilder implements ConditionElementBuilder {

  private JunctionBuilder defaultJunction;

  private final List<ConditionElementBuilder> elementBuilders;

  protected ConditionsBuilder() {
    this.defaultJunction = new AndBuilder();
    this.elementBuilders = new ArrayList<>();
  }

  protected ConditionsBuilder(ConditionBuilder conditionBuilder) {
    this.defaultJunction = new AndBuilder();
    this.elementBuilders = new ArrayList<>();
    this.elementBuilders.add(this.defaultJunction);
    this.elementBuilders.add(conditionBuilder);
  }

  public <T> ConditionsBuilder eq(StringLambda<T> lambda, String value) {
    return addConditionBuilder(lambda, eq, value);
  }

  public <T> ConditionsBuilder eq(NumberLambda<T> lambda, Number value) {
    return addConditionBuilder(lambda, eq, value);
  }

  public <T> ConditionsBuilder eq(BooleanLambda<T> lambda, Boolean value) {
    return addConditionBuilder(lambda, eq, value);
  }

  public <T> ConditionsBuilder eq(ColumnBuilder<T, String> left, String value) {
    return addConditionBuilder(left, eq, value);
  }

  public <T> ConditionsBuilder eq(ColumnBuilder<T, Number> left, Number value) {
    return addConditionBuilder(left, eq, value);
  }

  public <T> ConditionsBuilder eq(ColumnBuilder<T, Boolean> left, Boolean value) {
    return addConditionBuilder(left, eq, value);
  }

  public <T, O> ConditionsBuilder eq(NumberLambda<T> left, NumberLambda<O> right) {
    return addConditionBuilder(left, eq, right);
  }

  public <T, O> ConditionsBuilder eq(StringLambda<T> left, StringLambda<O> right) {
    return addConditionBuilder(left, eq, right);
  }

  public <T, O> ConditionsBuilder eq(BooleanLambda<T> left, BooleanLambda<O> right) {
    return addConditionBuilder(left, eq, right);
  }

  public <T, O> ConditionsBuilder eq(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return addConditionBuilder(left, eq, right);
  }

  public <T, O> ConditionsBuilder eq(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return addConditionBuilder(left, eq, right);
  }

  public <T, O> ConditionsBuilder eq(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return addConditionBuilder(left, eq, right);
  }

  public <T, O> ConditionsBuilder eq(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return addConditionBuilder(left, eq, right);
  }

  public <T, O> ConditionsBuilder eq(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return addConditionBuilder(left, eq, right);
  }

  public <T, O> ConditionsBuilder eq(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return addConditionBuilder(left, eq, right);
  }

  public <T, O, R> ConditionsBuilder eq(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return addConditionBuilder(left, eq, right);
  }

  public <T> ConditionsBuilder ne(StringLambda<T> lambda, String value) {
    return addConditionBuilder(lambda, ne, value);
  }

  public <T> ConditionsBuilder ne(NumberLambda<T> lambda, Number value) {
    return addConditionBuilder(lambda, ne, value);
  }

  public <T> ConditionsBuilder ne(BooleanLambda<T> lambda, Boolean value) {
    return addConditionBuilder(lambda, ne, value);
  }

  public <T> ConditionsBuilder ne(ColumnBuilder<T, String> left, String value) {
    return addConditionBuilder(left, ne, value);
  }

  public <T> ConditionsBuilder ne(ColumnBuilder<T, Number> left, Number value) {
    return addConditionBuilder(left, ne, value);
  }

  public <T> ConditionsBuilder ne(ColumnBuilder<T, Boolean> left, Boolean value) {
    return addConditionBuilder(left, ne, value);
  }

  public <T, O> ConditionsBuilder ne(NumberLambda<T> left, NumberLambda<O> right) {
    return addConditionBuilder(left, ne, right);
  }

  public <T, O> ConditionsBuilder ne(StringLambda<T> left, StringLambda<O> right) {
    return addConditionBuilder(left, ne, right);
  }

  public <T, O> ConditionsBuilder ne(BooleanLambda<T> left, BooleanLambda<O> right) {
    return addConditionBuilder(left, ne, right);
  }

  public <T, O> ConditionsBuilder ne(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return addConditionBuilder(left, ne, right);
  }

  public <T, O> ConditionsBuilder ne(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return addConditionBuilder(left, ne, right);
  }

  public <T, O> ConditionsBuilder ne(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return addConditionBuilder(left, ne, right);
  }

  public <T, O> ConditionsBuilder ne(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return addConditionBuilder(left, ne, right);
  }

  public <T, O> ConditionsBuilder ne(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return addConditionBuilder(left, ne, right);
  }

  public <T, O> ConditionsBuilder ne(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return addConditionBuilder(left, ne, right);
  }

  public <T, O, R> ConditionsBuilder ne(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return addConditionBuilder(left, ne, right);
  }

  public <T> ConditionsBuilder gt(NumberLambda<T> lambda, Number value) {
    return addConditionBuilder(lambda, gt, value);
  }

  public <T> ConditionsBuilder gt(BooleanLambda<T> lambda, Boolean value) {
    return addConditionBuilder(lambda, gt, value);
  }

  public <T> ConditionsBuilder gt(StringLambda<T> lambda, String value) {
    return addConditionBuilder(lambda, gt, value);
  }

  public <T> ConditionsBuilder gt(ColumnBuilder<T, String> left, String value) {
    return addConditionBuilder(left, gt, value);
  }

  public <T> ConditionsBuilder gt(ColumnBuilder<T, Number> left, Number value) {
    return addConditionBuilder(left, gt, value);
  }

  public <T> ConditionsBuilder gt(ColumnBuilder<T, Boolean> left, Boolean value) {
    return addConditionBuilder(left, gt, value);
  }

  public <T, O> ConditionsBuilder gt(NumberLambda<T> left, NumberLambda<O> right) {
    return addConditionBuilder(left, gt, right);
  }

  public <T, O> ConditionsBuilder gt(StringLambda<T> left, StringLambda<O> right) {
    return addConditionBuilder(left, gt, right);
  }

  public <T, O> ConditionsBuilder gt(BooleanLambda<T> left, BooleanLambda<O> right) {
    return addConditionBuilder(left, gt, right);
  }

  public <T, O> ConditionsBuilder gt(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return addConditionBuilder(left, gt, right);
  }

  public <T, O> ConditionsBuilder gt(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return addConditionBuilder(left, gt, right);
  }

  public <T, O> ConditionsBuilder gt(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return addConditionBuilder(left, gt, right);
  }

  public <T, O> ConditionsBuilder gt(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return addConditionBuilder(left, gt, right);
  }

  public <T, O> ConditionsBuilder gt(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return addConditionBuilder(left, gt, right);
  }

  public <T, O> ConditionsBuilder gt(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return addConditionBuilder(left, gt, right);
  }

  public <T, O, R> ConditionsBuilder gt(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return addConditionBuilder(left, gt, right);
  }


  public <T> ConditionsBuilder ge(NumberLambda<T> lambda, Number value) {
    return addConditionBuilder(lambda, ge, value);
  }

  public <T> ConditionsBuilder ge(BooleanLambda<T> lambda, Boolean value) {
    return addConditionBuilder(lambda, ge, value);
  }

  public <T> ConditionsBuilder ge(StringLambda<T> lambda, String value) {
    return addConditionBuilder(lambda, ge, value);
  }

  public <T> ConditionsBuilder ge(ColumnBuilder<T, String> left, String value) {
    return addConditionBuilder(left, ge, value);
  }

  public <T> ConditionsBuilder ge(ColumnBuilder<T, Number> left, Number value) {
    return addConditionBuilder(left, ge, value);
  }

  public <T> ConditionsBuilder ge(ColumnBuilder<T, Boolean> left, Boolean value) {
    return addConditionBuilder(left, ge, value);
  }

  public <T, O> ConditionsBuilder ge(NumberLambda<T> left, NumberLambda<O> right) {
    return addConditionBuilder(left, ge, right);
  }

  public <T, O> ConditionsBuilder ge(StringLambda<T> left, StringLambda<O> right) {
    return addConditionBuilder(left, ge, right);
  }

  public <T, O> ConditionsBuilder ge(BooleanLambda<T> left, BooleanLambda<O> right) {
    return addConditionBuilder(left, ge, right);
  }

  public <T, O> ConditionsBuilder ge(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return addConditionBuilder(left, ge, right);
  }

  public <T, O> ConditionsBuilder ge(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return addConditionBuilder(left, ge, right);
  }

  public <T, O> ConditionsBuilder ge(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return addConditionBuilder(left, ge, right);
  }

  public <T, O> ConditionsBuilder ge(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return addConditionBuilder(left, ge, right);
  }

  public <T, O> ConditionsBuilder ge(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return addConditionBuilder(left, ge, right);
  }

  public <T, O> ConditionsBuilder ge(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return addConditionBuilder(left, ge, right);
  }

  public <T, O, R> ConditionsBuilder ge(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return addConditionBuilder(left, ge, right);
  }

  public <T> ConditionsBuilder lt(NumberLambda<T> lambda, Number value) {
    return addConditionBuilder(lambda, lt, value);
  }

  public <T> ConditionsBuilder lt(BooleanLambda<T> lambda, Boolean value) {
    return addConditionBuilder(lambda, lt, value);
  }

  public <T> ConditionsBuilder lt(StringLambda<T> lambda, String value) {
    return addConditionBuilder(lambda, lt, value);
  }

  public <T> ConditionsBuilder lt(ColumnBuilder<T, String> left, String value) {
    return addConditionBuilder(left, ge, value);
  }

  public <T> ConditionsBuilder lt(ColumnBuilder<T, Number> left, Number value) {
    return addConditionBuilder(left, ge, value);
  }

  public <T> ConditionsBuilder lt(ColumnBuilder<T, Boolean> left, Boolean value) {
    return addConditionBuilder(left, ge, value);
  }

  public <T, O> ConditionsBuilder lt(NumberLambda<T> left, NumberLambda<O> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O> ConditionsBuilder lt(StringLambda<T> left, StringLambda<O> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O> ConditionsBuilder lt(BooleanLambda<T> left, BooleanLambda<O> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O> ConditionsBuilder lt(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O> ConditionsBuilder lt(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O> ConditionsBuilder lt(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O> ConditionsBuilder lt(DateLambda<T> left, Date right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O> ConditionsBuilder lt(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O> ConditionsBuilder lt(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O> ConditionsBuilder lt(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O, R> ConditionsBuilder lt(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T> ConditionsBuilder le(NumberLambda<T> lambda, Number value) {
    return addConditionBuilder(lambda, le, value);
  }

  public <T> ConditionsBuilder le(BooleanLambda<T> lambda, Boolean value) {
    return addConditionBuilder(lambda, le, value);
  }

  public <T> ConditionsBuilder le(StringLambda<T> lambda, String value) {
    return addConditionBuilder(lambda, le, value);
  }

  public <T> ConditionsBuilder le(ColumnBuilder<T, String> left, String value) {
    return addConditionBuilder(left, le, value);
  }

  public <T> ConditionsBuilder le(ColumnBuilder<T, Number> left, Number value) {
    return addConditionBuilder(left, le, value);
  }

  public <T> ConditionsBuilder le(ColumnBuilder<T, Boolean> left, Boolean value) {
    return addConditionBuilder(left, le, value);
  }

  public <T, O> ConditionsBuilder le(NumberLambda<T> left, NumberLambda<O> right) {
    return addConditionBuilder(left, le, right);
  }

  public <T, O> ConditionsBuilder le(StringLambda<T> left, StringLambda<O> right) {
    return addConditionBuilder(left, le, right);
  }

  public <T, O> ConditionsBuilder le(BooleanLambda<T> left, BooleanLambda<O> right) {
    return addConditionBuilder(left, le, right);
  }

  public <T, O> ConditionsBuilder le(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O> ConditionsBuilder le(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return addConditionBuilder(left, lt, right);
  }

  public <T, O> ConditionsBuilder le(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return addConditionBuilder(left, le, right);
  }

  public <T, O> ConditionsBuilder le(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return addConditionBuilder(left, le, right);
  }

  public <T, O> ConditionsBuilder le(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return addConditionBuilder(left, le, right);
  }

  public <T, O> ConditionsBuilder le(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return addConditionBuilder(left, le, right);
  }

  public <T, O, R> ConditionsBuilder le(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return addConditionBuilder(left, le, right);
  }

  public <T> ConditionsBuilder in(NumberLambda<T> lambda, Collection<Number> valueList) {
    return addConditionBuilder(lambda, in, valueList);
  }

  public <T> ConditionsBuilder in(StringLambda<T> lambda, Collection<String> valueList) {
    return addConditionBuilder(lambda, in, valueList);
  }

  public <T> ConditionsBuilder in(NumberLambda<T> lambda, Number... valueList) {
    return addConditionBuilder(lambda, in, asList(valueList));
  }

  public <T> ConditionsBuilder in(StringLambda<T> lambda, String... valueList) {
    return addConditionBuilder(lambda, in, asList(valueList));
  }

  public <T, R> ConditionsBuilder in(ColumnBuilder<T, R> left,
      Collection<R> valueList) {
    return addConditionBuilder(left, in, valueList);
  }

  public <T> ConditionsBuilder in(ColumnBuilder<T, Number> left, Number... valueList) {
    return addConditionBuilder(left, in, asList(valueList));
  }

  public <T> ConditionsBuilder in(ColumnBuilder<T, String> left, String... valueList) {
    return addConditionBuilder(left, in, asList(valueList));
  }

  public <T, R> ConditionsBuilder in(ColumnLambda<T, R> lambda,
      SubQueryType<T, R> subQuery) {
    return addConditionBuilder(lambda, in, subQuery.getColumnBuilder());
  }

  public <T> ConditionsBuilder notIn(
      NumberLambda<T> lambda, Collection<Number> valueList) {
    return addConditionBuilder(lambda, notIn, valueList);
  }

  public <T> ConditionsBuilder notIn(
      StringLambda<T> lambda, Collection<String> valueList) {
    return addConditionBuilder(lambda, notIn, valueList);
  }

  public <T> ConditionsBuilder notIn(StringLambda<T> lambda, String... valueList) {
    return addConditionBuilder(lambda, notIn, asList(valueList));
  }

  public <T> ConditionsBuilder notIn(NumberLambda<T> lambda, Number... valueList) {
    return addConditionBuilder(lambda, notIn, asList(valueList));
  }

  public <T, R> ConditionsBuilder notIn(ColumnBuilder<T, R> left,
      Collection<R> valueList) {
    return addConditionBuilder(left, notIn, valueList);
  }

  public <T> ConditionsBuilder notIn(ColumnBuilder<T, Number> left, Number... valueList) {
    return addConditionBuilder(left, notIn, asList(valueList));
  }

  public <T> ConditionsBuilder notIn(ColumnBuilder<T, String> left, String... valueList) {
    return addConditionBuilder(left, notIn, asList(valueList));
  }

  public <T> ConditionsBuilder notIn(LongLambda<T> lambda, Long... valueList) {
    return addConditionBuilder(lambda, notIn, asList(valueList));
  }

  public <T, R> ConditionsBuilder notIn(ColumnLambda<T, R> lambda,
      SubQueryType<T, R> subQuery) {
    return addConditionBuilder(lambda, notIn, subQuery.getColumnBuilder());
  }

  public <T> ConditionsBuilder like(StringLambda<T> lambda, LikeValue likeValue) {
    return addConditionBuilder(lambda, like, likeValue.build());
  }

  public <T> ConditionsBuilder like(ColumnBuilder<T, String> left, LikeValue likeValue) {
    return addConditionBuilder(left, like, likeValue.build());
  }

  public <T> ConditionsBuilder notLike(StringLambda<T> lambda, LikeValue likeValue) {
    return addConditionBuilder(lambda, notLike, likeValue.build());
  }

  public <T> ConditionsBuilder notLike(ColumnBuilder<T, String> left, LikeValue likeValue) {
    return addConditionBuilder(left, notLike, likeValue.build());
  }

  public <T> ConditionsBuilder between(DateLambda<T> lambda, DateRange range) {
    return addConditionBuilder(lambda, ge, range.getStart()).lt(lambda, range.getEnd());
  }

  public ConditionsBuilder and() {
    this.defaultJunction = new AndBuilder();
    return this;
  }

  public ConditionsBuilder and(Consumer<ConditionsBuilder> consumer) {
    ConditionsBuilder builder = new ConditionsBuilder();
    consumer.accept(builder);
    this.elementBuilders.add(new AndBuilder());
    this.elementBuilders.add(builder);
    return this;
  }

  public ConditionsBuilder or() {
    this.defaultJunction = new OrBuilder();
    return this;
  }

  public ConditionsBuilder or(Consumer<ConditionsBuilder> consumer) {
    ConditionsBuilder builder = new ConditionsBuilder();
    consumer.accept(builder);
    this.elementBuilders.add(new OrBuilder());
    this.elementBuilders.add(builder);
    return this;
  }

  private <T, R> ConditionsBuilder addConditionBuilder(
      SerializableLambda<T, R> lambda, ConditionMethod method, Object value) {
    this.elementBuilders.add(defaultJunction);
    this.elementBuilders.add(new ConditionBuilder(
        lambda.getColumnBuilder(),
        method,
        new ValueColumnBuilder<>(value)));
    return this;
  }

  private <T, R> ConditionsBuilder addConditionBuilder(
      SerializableLambda<T, R> lambdaLeft, ConditionMethod method,
      SerializableLambda<T, R> lambdaRight) {
    this.elementBuilders.add(defaultJunction);
    this.elementBuilders.add(new ConditionBuilder(
        lambdaLeft.getColumnBuilder(),
        method,
        lambdaRight.getColumnBuilder()));
    return this;
  }


  private <T, O, R> ConditionsBuilder addConditionBuilder(
      SerializableLambda<T, R> left, ConditionMethod method,
      ColumnBuilder<O, R> right) {
    this.elementBuilders.add(defaultJunction);
    this.elementBuilders.add(new ConditionBuilder(left.getColumnBuilder(), method, right));
    return this;
  }

  private <T, O, R> ConditionsBuilder addConditionBuilder(
      ColumnBuilder<T, R> left, ConditionMethod method,
      SerializableLambda<O, R> right) {
    this.elementBuilders.add(defaultJunction);
    this.elementBuilders.add(new ConditionBuilder(left, method, right.getColumnBuilder()));
    return this;
  }


  private <T, R> ConditionsBuilder addConditionBuilder(
      ColumnBuilder<T, R> left, ConditionMethod method, Object value) {
    this.elementBuilders.add(defaultJunction);
    this.elementBuilders.add(new ConditionBuilder(
        left,
        method,
        new ValueColumnBuilder<>(value)));
    return this;
  }

  private <T, O, R> ConditionsBuilder addConditionBuilder(
      ColumnBuilder<T, R> left, ConditionMethod method, ColumnBuilder<O, R> right) {
    this.elementBuilders.add(defaultJunction);
    this.elementBuilders.add(new ConditionBuilder(left, method, right));
    return this;
  }

  protected boolean isEmpty() {
    return this.elementBuilders.isEmpty();
  }

  protected boolean isSimpleCondition() {
    return this.elementBuilders.size() == 2 || this.elementBuilders.size() == 1;
  }

  @Override
  public Conditions build(EntityConfig config) {
    if (this.isEmpty()) {
      return Conditions.empty();
    }
    this.elementBuilders.remove(0);
    List<ConditionElement> elements = this.elementBuilders.stream()
        .flatMap(elementBuilder -> {
          if (elementBuilder instanceof ConditionsBuilder) {
            ConditionsBuilder conditionsBuilder = (ConditionsBuilder) elementBuilder;
            if (conditionsBuilder.isSimpleCondition()) {
              return conditionsBuilder.elementBuilders.stream();
            }
          }
          return Stream.of(elementBuilder);
        })
        .map(elementBuilder -> elementBuilder.build(config))
        .collect(toList());
    return new Conditions(elements);
  }
}
