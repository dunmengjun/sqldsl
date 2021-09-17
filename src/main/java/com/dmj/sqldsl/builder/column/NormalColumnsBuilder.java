package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NormalColumnsBuilder implements ColumnsBuilder {

  private final List<ColumnLambda<?, ?>> functions;

  @Override
  public List<Column> build(EntityConfig config) {
    return functions.stream()
        .map(x -> x.getColumnBuilder().build(config))
        .collect(Collectors.toList());
  }
}
