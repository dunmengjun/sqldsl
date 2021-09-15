package com.dmj.sqldsl.builder.condition;

import static com.dmj.sqldsl.utils.AssertUtils.assertTypeMatched;

import com.dmj.sqldsl.builder.column.ColumnFunction;
import com.dmj.sqldsl.builder.column.ValueColumnBuilder;
import com.dmj.sqldsl.model.condition.ConditionMethod;

public class ConditionBuilders {

  public static <T, R> ConditionsBuilder eq(ColumnFunction<T, R> function, Object object) {
    assertTypeMatched(function.getReturnType(), object.getClass());
    return new ConditionsBuilder(
        new ConditionBuilder(function.getColumnBuilder(), new ValueColumnBuilder(object))
    );
  }

  public static <T, R, O> ConditionsBuilder eq(ColumnFunction<T, R> left,
      ColumnFunction<O, R> right) {
    assertTypeMatched(left.getReturnType(), right.getReturnType());
    return new ConditionsBuilder(new ConditionBuilder(left.getColumnBuilder(),
        right.getColumnBuilder()));
  }

  public static <T, R> ConditionsBuilder gt(ColumnFunction<T, R> function, Object object) {
    assertTypeMatched(function.getReturnType(), object.getClass());
    return new ConditionsBuilder(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.gt,
            new ValueColumnBuilder(object))
    );
  }

  public static <T, R> ConditionsBuilder ge(ColumnFunction<T, R> function, Object object) {
    assertTypeMatched(function.getReturnType(), object.getClass());
    return new ConditionsBuilder(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.ge,
            new ValueColumnBuilder(object))
    );
  }

  public static <T, R> ConditionsBuilder lt(ColumnFunction<T, R> function, Object object) {
    assertTypeMatched(function.getReturnType(), object.getClass());
    return new ConditionsBuilder(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.lt,
            new ValueColumnBuilder(object))
    );
  }

  public static <T, R> ConditionsBuilder le(ColumnFunction<T, R> function, Object object) {
    assertTypeMatched(function.getReturnType(), object.getClass());
    return new ConditionsBuilder(
        new ConditionBuilder(
            function.getColumnBuilder(),
            ConditionMethod.le,
            new ValueColumnBuilder(object))
    );
  }
}
