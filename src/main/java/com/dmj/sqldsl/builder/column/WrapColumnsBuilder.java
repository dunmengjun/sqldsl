package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WrapColumnsBuilder implements ColumnsBuilder {

  private final List<ColumnBuilder> columnBuilders;

  @Override
  public List<Column> build(EntityConfig config) {
    return columnBuilders.stream()
        .map(x -> x.build(config))
        .collect(Collectors.toList());
  }
}
