package com.dmj.sqldsl.builder.condition;

import com.dmj.sqldsl.builder.column.ValueColumnBuilder;
import com.dmj.sqldsl.builder.column.type.BooleanFunction;
import com.dmj.sqldsl.builder.column.type.NumberFunction;
import com.dmj.sqldsl.builder.column.type.StringFunction;
import com.dmj.sqldsl.model.condition.ConditionMethod;
import java.util.List;

public class ConditionBuilders {

  public static <T> ConditionalExpression eq(StringFunction<T> function, String value) {
    return new ConditionalExpression(
        new ConditionBuilder(function.getColumnBuilder(), new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression eq(NumberFunction<T> function, Number value) {
    return new ConditionalExpression(
        new ConditionBuilder(function.getColumnBuilder(), new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression eq(BooleanFunction<T> function, Boolean value) {
    return new ConditionalExpression(
        new ConditionBuilder(function.getColumnBuilder(), new ValueColumnBuilder(value))
    );
  }

  public static <T, O> ConditionalExpression eq(NumberFunction<T> left,
      NumberFunction<O> right) {
    return new ConditionalExpression(new ConditionBuilder(left.getColumnBuilder(),
        right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression eq(StringFunction<T> left,
      StringFunction<O> right) {
    return new ConditionalExpression(new ConditionBuilder(left.getColumnBuilder(),
        right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression eq(BooleanFunction<T> left,
      BooleanFunction<O> right) {
    return new ConditionalExpression(new ConditionBuilder(left.getColumnBuilder(),
        right.getColumnBuilder()));
  }

  public static <T> ConditionalExpression gt(NumberFunction<T> function, Number value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.gt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression gt(BooleanFunction<T> function, Boolean value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.gt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression gt(StringFunction<T> function, String value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.gt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T, O> ConditionalExpression gt(NumberFunction<T> left,
      NumberFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.gt,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression gt(StringFunction<T> left,
      StringFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.gt,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression gt(BooleanFunction<T> left,
      BooleanFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.gt,
            right.getColumnBuilder()));
  }


  public static <T> ConditionalExpression ge(NumberFunction<T> function, Number value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.ge,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression ge(BooleanFunction<T> function, Boolean value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.ge,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression ge(StringFunction<T> function, String value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.ge,
            new ValueColumnBuilder(value))
    );
  }

  public static <T, O> ConditionalExpression ge(NumberFunction<T> left,
      NumberFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.ge,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression ge(StringFunction<T> left,
      StringFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.ge,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression ge(BooleanFunction<T> left,
      BooleanFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.ge,
            right.getColumnBuilder()));
  }

  public static <T> ConditionalExpression lt(NumberFunction<T> function, Number value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.lt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression lt(BooleanFunction<T> function, Boolean value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.lt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression lt(StringFunction<T> function, String value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.lt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T, O> ConditionalExpression lt(NumberFunction<T> left,
      NumberFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.lt,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression lt(StringFunction<T> left,
      StringFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.lt,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression lt(BooleanFunction<T> left,
      BooleanFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.lt,
            right.getColumnBuilder()));
  }

  public static <T> ConditionalExpression le(NumberFunction<T> function, Number value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.le,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression le(BooleanFunction<T> function, Boolean value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.le,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression le(StringFunction<T> function, String value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.le,
            new ValueColumnBuilder(value))
    );
  }

  public static <T, O> ConditionalExpression le(NumberFunction<T> left,
      NumberFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.le,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression le(StringFunction<T> left,
      StringFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.le,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression le(BooleanFunction<T> left,
      BooleanFunction<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.le,
            right.getColumnBuilder()));
  }

  public static <T> ConditionalExpression in(NumberFunction<T> function, List<Number> valueList) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.in,
            new ValueColumnBuilder(valueList))
    );
  }

  public static <T> ConditionalExpression in(StringFunction<T> function, List<String> valueList) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.in,
            new ValueColumnBuilder(valueList))
    );
  }
}
