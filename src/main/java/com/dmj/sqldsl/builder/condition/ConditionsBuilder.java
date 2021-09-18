package com.dmj.sqldsl.builder.condition;

import static com.dmj.sqldsl.model.condition.ConditionMethod.ge;
import static com.dmj.sqldsl.model.condition.ConditionMethod.in;
import static com.dmj.sqldsl.model.condition.ConditionMethod.like;
import static com.dmj.sqldsl.model.condition.ConditionMethod.notIn;
import static com.dmj.sqldsl.model.condition.ConditionMethod.notLike;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

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

  public <T, R> ConditionsBuilder eq(ColumnLambda<T, R> lambda, Object object) {
    return addConditionBuilder(lambda, ConditionMethod.eq, object);
  }

  public <T, R, O, K> ConditionsBuilder eq(ColumnLambda<T, R> left,
      ColumnLambda<O, K> right) {
    return addConditionBuilder(left, ConditionMethod.eq, right);
  }

  public <T> ConditionsBuilder gt(NumberLambda<T> lambda, Number value) {
    return addConditionBuilder(lambda, ConditionMethod.gt, value);
  }

  public <T> ConditionsBuilder gt(BooleanLambda<T> lambda, Boolean value) {
    return addConditionBuilder(lambda, ConditionMethod.gt, value);
  }

  public <T> ConditionsBuilder gt(StringLambda<T> lambda, String value) {
    return addConditionBuilder(lambda, ConditionMethod.gt, value);
  }

  public <T, O> ConditionsBuilder gt(NumberLambda<T> left, NumberLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.gt, right);
  }

  public <T, O> ConditionsBuilder gt(StringLambda<T> left, StringLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.gt, right);
  }

  public <T, O> ConditionsBuilder gt(BooleanLambda<T> left, BooleanLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.gt, right);
  }

  public <T> ConditionsBuilder ge(NumberLambda<T> lambda, Number value) {
    return addConditionBuilder(lambda, ConditionMethod.ge, value);
  }

  public <T> ConditionsBuilder ge(BooleanLambda<T> lambda, Boolean value) {
    return addConditionBuilder(lambda, ConditionMethod.ge, value);
  }

  public <T> ConditionsBuilder ge(StringLambda<T> lambda, String value) {
    return addConditionBuilder(lambda, ConditionMethod.ge, value);
  }

  public <T, O> ConditionsBuilder ge(NumberLambda<T> left, NumberLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.ge, right);
  }

  public <T, O> ConditionsBuilder ge(StringLambda<T> left, StringLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.ge, right);
  }

  public <T, O> ConditionsBuilder ge(BooleanLambda<T> left, BooleanLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.ge, right);
  }

  public <T> ConditionsBuilder lt(DateLambda<T> lambda, Date value) {
    return addConditionBuilder(lambda, ConditionMethod.lt, value);
  }

  public <T> ConditionsBuilder lt(NumberLambda<T> lambda, Number value) {
    return addConditionBuilder(lambda, ConditionMethod.lt, value);
  }

  public <T> ConditionsBuilder lt(BooleanLambda<T> lambda, Boolean value) {
    return addConditionBuilder(lambda, ConditionMethod.lt, value);
  }

  public <T> ConditionsBuilder lt(StringLambda<T> lambda, String value) {
    return addConditionBuilder(lambda, ConditionMethod.lt, value);
  }

  public <T, O> ConditionsBuilder lt(NumberLambda<T> left, NumberLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.lt, right);
  }

  public <T, O> ConditionsBuilder lt(StringLambda<T> left, StringLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.lt, right);
  }

  public <T, O> ConditionsBuilder lt(BooleanLambda<T> left, BooleanLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.lt, right);
  }

  public <T> ConditionsBuilder le(NumberLambda<T> lambda, Number value) {
    return addConditionBuilder(lambda, ConditionMethod.le, value);
  }

  public <T> ConditionsBuilder le(BooleanLambda<T> lambda, Boolean value) {
    return addConditionBuilder(lambda, ConditionMethod.le, value);
  }

  public <T> ConditionsBuilder le(StringLambda<T> lambda, String value) {
    return addConditionBuilder(lambda, ConditionMethod.le, value);
  }

  public <T, O> ConditionsBuilder le(NumberLambda<T> left, NumberLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.le, right);
  }

  public <T, O> ConditionsBuilder le(StringLambda<T> left, StringLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.le, right);
  }

  public <T, O> ConditionsBuilder le(BooleanLambda<T> left, BooleanLambda<O> right) {
    return addConditionBuilder(left, ConditionMethod.le, right);
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

  public <T> ConditionsBuilder notIn(NumberLambda<T> lambda, Collection<Number> valueList) {
    return addConditionBuilder(lambda, notIn, valueList);
  }

  public <T> ConditionsBuilder notIn(StringLambda<T> lambda, Collection<String> valueList) {
    return addConditionBuilder(lambda, notIn, valueList);
  }

  public <T> ConditionsBuilder notIn(StringLambda<T> lambda, String... valueList) {
    return addConditionBuilder(lambda, notIn, asList(valueList));
  }

  public <T> ConditionsBuilder notIn(NumberLambda<T> lambda, Number... valueList) {
    return addConditionBuilder(lambda, notIn, asList(valueList));
  }

  public <T> ConditionsBuilder notIn(LongLambda<T> lambda, Long... valueList) {
    return addConditionBuilder(lambda, notIn, asList(valueList));
  }

  public <T> ConditionsBuilder like(StringLambda<T> lambda, LikeValue likeValue) {
    return addConditionBuilder(lambda, like, likeValue.build());
  }

  public <T> ConditionsBuilder notLike(StringLambda<T> lambda, LikeValue likeValue) {
    return addConditionBuilder(lambda, notLike, likeValue.build());
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

  private ConditionsBuilder addConditionBuilder(
      SerializableLambda lambda, ConditionMethod method, Object value) {
    this.elementBuilders.add(defaultJunction);
    this.elementBuilders.add(new ConditionBuilder(
        lambda.getColumnBuilder(),
        method,
        new ValueColumnBuilder(value)));
    return this;
  }

  private ConditionsBuilder addConditionBuilder(
      SerializableLambda lambdaLeft, ConditionMethod method, SerializableLambda lambdaRight) {
    this.elementBuilders.add(defaultJunction);
    this.elementBuilders.add(new ConditionBuilder(
        lambdaLeft.getColumnBuilder(),
        method,
        lambdaRight.getColumnBuilder()));
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
