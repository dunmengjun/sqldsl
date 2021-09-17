package com.dmj.sqldsl.builder.condition;

import com.dmj.sqldsl.builder.column.ValueColumnBuilder;
import com.dmj.sqldsl.builder.column.type.BooleanLambda;
import com.dmj.sqldsl.builder.column.type.NumberLambda;
import com.dmj.sqldsl.builder.column.type.StringLambda;
import com.dmj.sqldsl.model.condition.ConditionMethod;
import java.util.List;

public class ConditionBuilders {

  public static <T> ConditionalExpression eq(StringLambda<T> function, String value) {
    return new ConditionalExpression(
        new ConditionBuilder(function.getColumnBuilder(), new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression eq(NumberLambda<T> function, Number value) {
    return new ConditionalExpression(
        new ConditionBuilder(function.getColumnBuilder(), new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression eq(BooleanLambda<T> function, Boolean value) {
    return new ConditionalExpression(
        new ConditionBuilder(function.getColumnBuilder(), new ValueColumnBuilder(value))
    );
  }

  public static <T, O> ConditionalExpression eq(NumberLambda<T> left,
      NumberLambda<O> right) {
    return new ConditionalExpression(new ConditionBuilder(left.getColumnBuilder(),
        right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression eq(StringLambda<T> left,
      StringLambda<O> right) {
    return new ConditionalExpression(new ConditionBuilder(left.getColumnBuilder(),
        right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression eq(BooleanLambda<T> left,
      BooleanLambda<O> right) {
    return new ConditionalExpression(new ConditionBuilder(left.getColumnBuilder(),
        right.getColumnBuilder()));
  }

  public static <T> ConditionalExpression gt(NumberLambda<T> function, Number value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.gt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression gt(BooleanLambda<T> function, Boolean value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.gt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression gt(StringLambda<T> function, String value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.gt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T, O> ConditionalExpression gt(NumberLambda<T> left,
      NumberLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.gt,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression gt(StringLambda<T> left,
      StringLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.gt,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression gt(BooleanLambda<T> left,
      BooleanLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.gt,
            right.getColumnBuilder()));
  }


  public static <T> ConditionalExpression ge(NumberLambda<T> function, Number value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.ge,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression ge(BooleanLambda<T> function, Boolean value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.ge,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression ge(StringLambda<T> function, String value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.ge,
            new ValueColumnBuilder(value))
    );
  }

  public static <T, O> ConditionalExpression ge(NumberLambda<T> left,
      NumberLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.ge,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression ge(StringLambda<T> left,
      StringLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.ge,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression ge(BooleanLambda<T> left,
      BooleanLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.ge,
            right.getColumnBuilder()));
  }

  public static <T> ConditionalExpression lt(NumberLambda<T> function, Number value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.lt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression lt(BooleanLambda<T> function, Boolean value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.lt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression lt(StringLambda<T> function, String value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.lt,
            new ValueColumnBuilder(value))
    );
  }

  public static <T, O> ConditionalExpression lt(NumberLambda<T> left,
      NumberLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.lt,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression lt(StringLambda<T> left,
      StringLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.lt,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression lt(BooleanLambda<T> left,
      BooleanLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.lt,
            right.getColumnBuilder()));
  }

  public static <T> ConditionalExpression le(NumberLambda<T> function, Number value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.le,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression le(BooleanLambda<T> function, Boolean value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.le,
            new ValueColumnBuilder(value))
    );
  }

  public static <T> ConditionalExpression le(StringLambda<T> function, String value) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.le,
            new ValueColumnBuilder(value))
    );
  }

  public static <T, O> ConditionalExpression le(NumberLambda<T> left,
      NumberLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.le,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression le(StringLambda<T> left,
      StringLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.le,
            right.getColumnBuilder()));
  }

  public static <T, O> ConditionalExpression le(BooleanLambda<T> left,
      BooleanLambda<O> right) {
    return new ConditionalExpression(
        new ConditionBuilder(
            left.getColumnBuilder(),
            ConditionMethod.le,
            right.getColumnBuilder()));
  }

  public static <T> ConditionalExpression in(NumberLambda<T> function, List<Number> valueList) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.in,
            new ValueColumnBuilder(valueList))
    );
  }

  public static <T> ConditionalExpression in(StringLambda<T> function, List<String> valueList) {
    return new ConditionalExpression(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.in,
            new ValueColumnBuilder(valueList))
    );
  }
}
