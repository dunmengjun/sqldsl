package com.dmj.sqldsl.builder.column;

import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.Function;
import com.dmj.sqldsl.model.column.FunctionColumn;
import java.util.List;

public class FunctionColumnBuilder<T, R> implements ColumnBuilder<T, R> {

  private final Function function;
  private final List<ColumnBuilder<?, ?>> params;

  public FunctionColumnBuilder(Function function, List<ColumnBuilder<?, ?>> params) {
    this.function = function;
    this.params = params;
  }

  @Override
  public Column build(EntityConfig config) {
    List<Column> columns = params.stream()
        .map(columnBuilder -> columnBuilder.build(config))
        .collect(toList());
    return new FunctionColumn(function, columns);
  }
}
