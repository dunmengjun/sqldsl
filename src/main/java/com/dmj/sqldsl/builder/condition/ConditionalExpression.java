package com.dmj.sqldsl.builder.condition;

import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.ValueColumnBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnFunction;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.condition.ConditionElement;
import com.dmj.sqldsl.model.condition.Conditions;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ConditionalExpression implements ConditionElementBuilder {

  private JunctionBuilder defaultJunction;

  private final List<ConditionElementBuilder> elementBuilders;

  public ConditionalExpression() {
    this.defaultJunction = new AndBuilder();
    this.elementBuilders = new ArrayList<>();
  }

  protected ConditionalExpression(ConditionBuilder conditionBuilder) {
    this.defaultJunction = new AndBuilder();
    this.elementBuilders = new ArrayList<>();
    this.elementBuilders.add(this.defaultJunction);
    this.elementBuilders.add(conditionBuilder);
  }

  public <T, R> ConditionalExpression eq(ColumnFunction<T, R> function, Object object) {
    this.elementBuilders.add(defaultJunction);
    this.elementBuilders.add(
        new ConditionBuilder(function.getColumnBuilder(),
            new ValueColumnBuilder(object))
    );
    return this;
  }

  public <T, R, O, K> ConditionalExpression eq(ColumnFunction<T, R> left,
      ColumnFunction<O, K> right) {
    this.elementBuilders.add(defaultJunction);
    this.elementBuilders.add(new ConditionBuilder(left.getColumnBuilder(),
        right.getColumnBuilder()));
    return this;
  }

  public ConditionalExpression and() {
    this.defaultJunction = new AndBuilder();
    return this;
  }

  public ConditionalExpression and(Consumer<ConditionalExpression> consumer) {
    ConditionalExpression builder = new ConditionalExpression();
    consumer.accept(builder);
    this.elementBuilders.add(new AndBuilder());
    this.elementBuilders.add(builder);
    return this;
  }

  public ConditionalExpression or() {
    this.defaultJunction = new OrBuilder();
    return this;
  }

  public ConditionalExpression or(Consumer<ConditionalExpression> consumer) {
    ConditionalExpression builder = new ConditionalExpression();
    consumer.accept(builder);
    this.elementBuilders.add(new OrBuilder());
    this.elementBuilders.add(builder);
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
          if (elementBuilder instanceof ConditionalExpression) {
            ConditionalExpression conditionalExpression = (ConditionalExpression) elementBuilder;
            if (conditionalExpression.isSimpleCondition()) {
              return conditionalExpression.elementBuilders.stream();
            }
          }
          return Stream.of(elementBuilder);
        })
        .map(elementBuilder -> elementBuilder.build(config))
        .collect(toList());
    return new Conditions(elements);
  }
}
