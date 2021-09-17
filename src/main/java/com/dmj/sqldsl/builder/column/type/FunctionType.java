package com.dmj.sqldsl.builder.column.type;

import com.dmj.sqldsl.model.column.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FunctionType<T, R> {

  private Function function;
  private ColumnLambda<T, ?> columnLambda;
}
