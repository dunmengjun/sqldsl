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

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.DateRange;
import com.dmj.sqldsl.builder.column.LikeValue;
import com.dmj.sqldsl.builder.column.SubQueryColumnBuilder;
import com.dmj.sqldsl.builder.column.ValueColumnBuilder;
import com.dmj.sqldsl.builder.column.type.BooleanLambda;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.DateLambda;
import com.dmj.sqldsl.builder.column.type.LongLambda;
import com.dmj.sqldsl.builder.column.type.NumberLambda;
import com.dmj.sqldsl.builder.column.type.StringLambda;
import com.dmj.sqldsl.builder.column.type.TypedLambda;
import com.dmj.sqldsl.model.condition.ConditionMethod;
import java.util.Collection;

public class ConditionBuilders {

  public static <T> ConditionsBuilder eq(StringLambda<T> lambda, String value) {
    return create(lambda, eq, value);
  }

  public static <T> ConditionsBuilder eq(NumberLambda<T> lambda, Number value) {
    return create(lambda, eq, value);
  }

  public static <T> ConditionsBuilder eq(BooleanLambda<T> lambda, Boolean value) {
    return create(lambda, eq, value);
  }

  public static <T> ConditionsBuilder eq(ColumnBuilder<T, String> left, String value) {
    return create(left, eq, value);
  }

  public static <T> ConditionsBuilder eq(ColumnBuilder<T, Number> left, Number value) {
    return create(left, eq, value);
  }

  public static <T> ConditionsBuilder eq(ColumnBuilder<T, Boolean> left, Boolean value) {
    return create(left, eq, value);
  }

  public static <T, O> ConditionsBuilder eq(NumberLambda<T> left, NumberLambda<O> right) {
    return create(left, eq, right);
  }

  public static <T, O> ConditionsBuilder eq(StringLambda<T> left, StringLambda<O> right) {
    return create(left, eq, right);
  }

  public static <T, O> ConditionsBuilder eq(BooleanLambda<T> left, BooleanLambda<O> right) {
    return create(left, eq, right);
  }

  public static <T, O> ConditionsBuilder eq(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return create(left, eq, right);
  }

  public static <T, O> ConditionsBuilder eq(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return create(left, eq, right);
  }

  public static <T, O> ConditionsBuilder eq(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return create(left, eq, right);
  }

  public static <T, O> ConditionsBuilder eq(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return create(left, eq, right);
  }

  public static <T, O> ConditionsBuilder eq(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return create(left, eq, right);
  }

  public static <T, O> ConditionsBuilder eq(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return create(left, eq, right);
  }

  public static <T, O, R> ConditionsBuilder eq(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return create(left, eq, right);
  }

  public static <T> ConditionsBuilder ne(StringLambda<T> lambda, String value) {
    return create(lambda, ne, value);
  }

  public static <T> ConditionsBuilder ne(NumberLambda<T> lambda, Number value) {
    return create(lambda, ne, value);
  }

  public static <T> ConditionsBuilder ne(BooleanLambda<T> lambda, Boolean value) {
    return create(lambda, ne, value);
  }

  public static <T> ConditionsBuilder ne(ColumnBuilder<T, String> left, String value) {
    return create(left, ne, value);
  }

  public static <T> ConditionsBuilder ne(ColumnBuilder<T, Number> left, Number value) {
    return create(left, ne, value);
  }

  public static <T> ConditionsBuilder ne(ColumnBuilder<T, Boolean> left, Boolean value) {
    return create(left, ne, value);
  }

  public static <T, O> ConditionsBuilder ne(NumberLambda<T> left, NumberLambda<O> right) {
    return create(left, ne, right);
  }

  public static <T, O> ConditionsBuilder ne(StringLambda<T> left, StringLambda<O> right) {
    return create(left, ne, right);
  }

  public static <T, O> ConditionsBuilder ne(BooleanLambda<T> left, BooleanLambda<O> right) {
    return create(left, ne, right);
  }

  public static <T, O> ConditionsBuilder ne(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return create(left, ne, right);
  }

  public static <T, O> ConditionsBuilder ne(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return create(left, ne, right);
  }

  public static <T, O> ConditionsBuilder ne(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return create(left, ne, right);
  }

  public static <T, O> ConditionsBuilder ne(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return create(left, ne, right);
  }

  public static <T, O> ConditionsBuilder ne(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return create(left, ne, right);
  }

  public static <T, O> ConditionsBuilder ne(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return create(left, ne, right);
  }

  public static <T, O, R> ConditionsBuilder ne(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return create(left, ne, right);
  }

  public static <T> ConditionsBuilder gt(NumberLambda<T> lambda, Number value) {
    return create(lambda, gt, value);
  }

  public static <T> ConditionsBuilder gt(BooleanLambda<T> lambda, Boolean value) {
    return create(lambda, gt, value);
  }

  public static <T> ConditionsBuilder gt(StringLambda<T> lambda, String value) {
    return create(lambda, gt, value);
  }

  public static <T> ConditionsBuilder gt(ColumnBuilder<T, String> left, String value) {
    return create(left, gt, value);
  }

  public static <T> ConditionsBuilder gt(ColumnBuilder<T, Number> left, Number value) {
    return create(left, gt, value);
  }

  public static <T> ConditionsBuilder gt(ColumnBuilder<T, Boolean> left, Boolean value) {
    return create(left, gt, value);
  }

  public static <T, O> ConditionsBuilder gt(NumberLambda<T> left, NumberLambda<O> right) {
    return create(left, gt, right);
  }

  public static <T, O> ConditionsBuilder gt(StringLambda<T> left, StringLambda<O> right) {
    return create(left, gt, right);
  }

  public static <T, O> ConditionsBuilder gt(BooleanLambda<T> left, BooleanLambda<O> right) {
    return create(left, gt, right);
  }

  public static <T, O> ConditionsBuilder gt(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return create(left, gt, right);
  }

  public static <T, O> ConditionsBuilder gt(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return create(left, gt, right);
  }

  public static <T, O> ConditionsBuilder gt(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return create(left, gt, right);
  }

  public static <T, O> ConditionsBuilder gt(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return create(left, gt, right);
  }

  public static <T, O> ConditionsBuilder gt(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return create(left, gt, right);
  }

  public static <T, O> ConditionsBuilder gt(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return create(left, gt, right);
  }

  public static <T, O, R> ConditionsBuilder gt(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return create(left, gt, right);
  }


  public static <T> ConditionsBuilder ge(NumberLambda<T> lambda, Number value) {
    return create(lambda, ge, value);
  }

  public static <T> ConditionsBuilder ge(BooleanLambda<T> lambda, Boolean value) {
    return create(lambda, ge, value);
  }

  public static <T> ConditionsBuilder ge(StringLambda<T> lambda, String value) {
    return create(lambda, ge, value);
  }

  public static <T> ConditionsBuilder ge(ColumnBuilder<T, String> left, String value) {
    return create(left, ge, value);
  }

  public static <T> ConditionsBuilder ge(ColumnBuilder<T, Number> left, Number value) {
    return create(left, ge, value);
  }

  public static <T> ConditionsBuilder ge(ColumnBuilder<T, Boolean> left, Boolean value) {
    return create(left, ge, value);
  }

  public static <T, O> ConditionsBuilder ge(NumberLambda<T> left, NumberLambda<O> right) {
    return create(left, ge, right);
  }

  public static <T, O> ConditionsBuilder ge(StringLambda<T> left, StringLambda<O> right) {
    return create(left, ge, right);
  }

  public static <T, O> ConditionsBuilder ge(BooleanLambda<T> left, BooleanLambda<O> right) {
    return create(left, ge, right);
  }

  public static <T, O> ConditionsBuilder ge(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return create(left, ge, right);
  }

  public static <T, O> ConditionsBuilder ge(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return create(left, ge, right);
  }

  public static <T, O> ConditionsBuilder ge(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return create(left, ge, right);
  }

  public static <T, O> ConditionsBuilder ge(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return create(left, ge, right);
  }

  public static <T, O> ConditionsBuilder ge(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return create(left, ge, right);
  }

  public static <T, O> ConditionsBuilder ge(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return create(left, ge, right);
  }

  public static <T, O, R> ConditionsBuilder ge(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return create(left, ge, right);
  }

  public static <T> ConditionsBuilder lt(NumberLambda<T> lambda, Number value) {
    return create(lambda, lt, value);
  }

  public static <T> ConditionsBuilder lt(BooleanLambda<T> lambda, Boolean value) {
    return create(lambda, lt, value);
  }

  public static <T> ConditionsBuilder lt(StringLambda<T> lambda, String value) {
    return create(lambda, lt, value);
  }

  public static <T> ConditionsBuilder lt(ColumnBuilder<T, String> left, String value) {
    return create(left, ge, value);
  }

  public static <T> ConditionsBuilder lt(ColumnBuilder<T, Number> left, Number value) {
    return create(left, ge, value);
  }

  public static <T> ConditionsBuilder lt(ColumnBuilder<T, Boolean> left, Boolean value) {
    return create(left, ge, value);
  }

  public static <T, O> ConditionsBuilder lt(NumberLambda<T> left, NumberLambda<O> right) {
    return create(left, lt, right);
  }

  public static <T, O> ConditionsBuilder lt(StringLambda<T> left, StringLambda<O> right) {
    return create(left, lt, right);
  }

  public static <T, O> ConditionsBuilder lt(BooleanLambda<T> left, BooleanLambda<O> right) {
    return create(left, lt, right);
  }

  public static <T, O> ConditionsBuilder lt(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return create(left, lt, right);
  }

  public static <T, O> ConditionsBuilder lt(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return create(left, lt, right);
  }

  public static <T, O> ConditionsBuilder lt(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return create(left, lt, right);
  }

  public static <T, O> ConditionsBuilder lt(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return create(left, lt, right);
  }

  public static <T, O> ConditionsBuilder lt(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return create(left, lt, right);
  }

  public static <T, O> ConditionsBuilder lt(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return create(left, lt, right);
  }

  public static <T, O, R> ConditionsBuilder lt(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return create(left, lt, right);
  }

  public static <T> ConditionsBuilder le(NumberLambda<T> lambda, Number value) {
    return create(lambda, le, value);
  }

  public static <T> ConditionsBuilder le(BooleanLambda<T> lambda, Boolean value) {
    return create(lambda, le, value);
  }

  public static <T> ConditionsBuilder le(StringLambda<T> lambda, String value) {
    return create(lambda, le, value);
  }

  public static <T> ConditionsBuilder le(ColumnBuilder<T, String> left, String value) {
    return create(left, le, value);
  }

  public static <T> ConditionsBuilder le(ColumnBuilder<T, Number> left, Number value) {
    return create(left, le, value);
  }

  public static <T> ConditionsBuilder le(ColumnBuilder<T, Boolean> left, Boolean value) {
    return create(left, le, value);
  }

  public static <T, O> ConditionsBuilder le(NumberLambda<T> left, NumberLambda<O> right) {
    return create(left, le, right);
  }

  public static <T, O> ConditionsBuilder le(StringLambda<T> left, StringLambda<O> right) {
    return create(left, le, right);
  }

  public static <T, O> ConditionsBuilder le(BooleanLambda<T> left, BooleanLambda<O> right) {
    return create(left, le, right);
  }

  public static <T, O> ConditionsBuilder le(StringLambda<T> left, ColumnBuilder<O, String> right) {
    return create(left, lt, right);
  }

  public static <T, O> ConditionsBuilder le(NumberLambda<T> left, ColumnBuilder<O, Number> right) {
    return create(left, lt, right);
  }

  public static <T, O> ConditionsBuilder le(BooleanLambda<T> left,
      ColumnBuilder<O, Boolean> right) {
    return create(left, le, right);
  }

  public static <T, O> ConditionsBuilder le(ColumnBuilder<T, String> left, StringLambda<O> right) {
    return create(left, le, right);
  }

  public static <T, O> ConditionsBuilder le(ColumnBuilder<T, Number> left, NumberLambda<O> right) {
    return create(left, le, right);
  }

  public static <T, O> ConditionsBuilder le(ColumnBuilder<T, Boolean> left,
      BooleanLambda<O> right) {
    return create(left, le, right);
  }

  public static <T, O, R> ConditionsBuilder le(ColumnBuilder<T, R> left,
      ColumnBuilder<O, R> right) {
    return create(left, le, right);
  }

  public static <T> ConditionsBuilder in(NumberLambda<T> lambda, Collection<Number> valueList) {
    return create(lambda, in, valueList);
  }

  public static <T> ConditionsBuilder in(StringLambda<T> lambda, Collection<String> valueList) {
    return create(lambda, in, valueList);
  }

  public static <T> ConditionsBuilder in(NumberLambda<T> lambda, Number... valueList) {
    return create(lambda, in, asList(valueList));
  }

  public static <T> ConditionsBuilder in(StringLambda<T> lambda, String... valueList) {
    return create(lambda, in, asList(valueList));
  }

  public static <T, R> ConditionsBuilder in(ColumnBuilder<T, R> left,
      Collection<R> valueList) {
    return create(left, in, valueList);
  }

  public static <T> ConditionsBuilder in(ColumnBuilder<T, Number> left, Number... valueList) {
    return create(left, in, asList(valueList));
  }

  public static <T> ConditionsBuilder in(ColumnBuilder<T, String> left, String... valueList) {
    return create(left, in, asList(valueList));
  }

  public static <T, R> ConditionsBuilder in(ColumnLambda<T, R> lambda,
      DslQueryBuilder queryBuilder) {
    return create(lambda, in, new SubQueryColumnBuilder<>(queryBuilder));
  }

  public static <T> ConditionsBuilder notIn(
      NumberLambda<T> lambda, Collection<Number> valueList) {
    return create(lambda, notIn, valueList);
  }

  public static <T> ConditionsBuilder notIn(
      StringLambda<T> lambda, Collection<String> valueList) {
    return create(lambda, notIn, valueList);
  }

  public static <T> ConditionsBuilder notIn(StringLambda<T> lambda, String... valueList) {
    return create(lambda, notIn, asList(valueList));
  }

  public static <T> ConditionsBuilder notIn(NumberLambda<T> lambda, Number... valueList) {
    return create(lambda, notIn, asList(valueList));
  }

  public static <T, R> ConditionsBuilder notIn(ColumnBuilder<T, R> left,
      Collection<R> valueList) {
    return create(left, notIn, valueList);
  }

  public static <T> ConditionsBuilder notIn(ColumnBuilder<T, Number> left, Number... valueList) {
    return create(left, notIn, asList(valueList));
  }

  public static <T> ConditionsBuilder notIn(ColumnBuilder<T, String> left, String... valueList) {
    return create(left, notIn, asList(valueList));
  }

  public static <T> ConditionsBuilder notIn(LongLambda<T> lambda, Long... valueList) {
    return create(lambda, notIn, asList(valueList));
  }

  public static <T, R> ConditionsBuilder notIn(ColumnLambda<T, R> lambda,
      DslQueryBuilder queryBuilder) {
    return create(lambda, notIn, new SubQueryColumnBuilder<>(queryBuilder));
  }

  public static <T> ConditionsBuilder like(StringLambda<T> lambda, LikeValue likeValue) {
    return create(lambda, like, likeValue.build());
  }

  public static <T> ConditionsBuilder like(ColumnBuilder<T, String> left, LikeValue likeValue) {
    return create(left, like, likeValue.build());
  }

  public static <T> ConditionsBuilder notLike(StringLambda<T> lambda, LikeValue likeValue) {
    return create(lambda, notLike, likeValue.build());
  }

  public static <T> ConditionsBuilder notLike(ColumnBuilder<T, String> left, LikeValue likeValue) {
    return create(left, notLike, likeValue.build());
  }

  public static <T> ConditionsBuilder between(DateLambda<T> lambda, DateRange range) {
    return create(lambda, ge, range.getStart()).lt(lambda, range.getEnd());
  }

  public static ConditionsBuilder empty() {
    return new ConditionsBuilder();
  }

  private static <T, R> ConditionsBuilder create(
      TypedLambda<T, R> lambda, ConditionMethod method, R value) {
    return new ConditionsBuilder(
        new ConditionBuilder(
            lambda.getColumnBuilder(),
            method,
            new ValueColumnBuilder<>(value)
        )
    );
  }

  private static <T, R> ConditionsBuilder create(
      ColumnBuilder<T, R> columnBuilder, ConditionMethod method, R value) {
    return new ConditionsBuilder(
        new ConditionBuilder(
            columnBuilder,
            method,
            new ValueColumnBuilder<>(value)
        )
    );
  }

  private static <T, R> ConditionsBuilder create(
      TypedLambda<T, R> lambda, ConditionMethod method, Collection<R> value) {
    return new ConditionsBuilder(
        new ConditionBuilder(
            lambda.getColumnBuilder(),
            method,
            new ValueColumnBuilder<>(value)
        )
    );
  }

  private static <T, R> ConditionsBuilder create(
      ColumnBuilder<T, R> left, ConditionMethod method, Collection<R> value) {
    return new ConditionsBuilder(
        new ConditionBuilder(
            left,
            method,
            new ValueColumnBuilder<>(value)
        )
    );
  }

  private static <T, O, R> ConditionsBuilder create(
      TypedLambda<T, R> lambdaLeft, ConditionMethod method,
      TypedLambda<O, R> lambdaRight) {
    return new ConditionsBuilder(
        new ConditionBuilder(
            lambdaLeft.getColumnBuilder(),
            method,
            lambdaRight.getColumnBuilder()
        )
    );
  }

  private static <T, O, R> ConditionsBuilder create(
      TypedLambda<T, R> lambdaLeft, ConditionMethod method,
      ColumnBuilder<O, R> columnBuilder) {
    return new ConditionsBuilder(
        new ConditionBuilder(
            lambdaLeft.getColumnBuilder(),
            method,
            columnBuilder
        )
    );
  }

  private static <T, O, R> ConditionsBuilder create(
      ColumnBuilder<O, R> columnBuilder,
      ConditionMethod method,
      TypedLambda<T, R> lambda) {
    return new ConditionsBuilder(
        new ConditionBuilder(
            columnBuilder,
            method,
            lambda.getColumnBuilder()
        )
    );
  }

  private static <T, O, R> ConditionsBuilder create(
      ColumnBuilder<O, R> left,
      ConditionMethod method,
      ColumnBuilder<T, R> right) {
    return new ConditionsBuilder(new ConditionBuilder(left, method, right));
  }
}
