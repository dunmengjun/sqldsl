package com.dmj.sqldsl.builder.condition;

import com.dmj.sqldsl.builder.column.ColumnFunction;
import com.dmj.sqldsl.builder.column.ValueColumnBuilder;

public class ConditionBuilders {

  public static <T, R> ConditionsBuilder eq(ColumnFunction<T, R> function, Object object) {
    return new ConditionsBuilder(
        new ConditionBuilder(function.getColumnBuilder(),
            new ValueColumnBuilder(object))
    );
  }

  public static <T, R, O, K> ConditionsBuilder eq(ColumnFunction<T, R> left,
      ColumnFunction<O, K> right) {
    return new ConditionsBuilder(new ConditionBuilder(left.getColumnBuilder(),
        right.getColumnBuilder()));
  }

}
