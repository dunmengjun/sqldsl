package com.dmj.sqldsl.executor.visitor;

import static java.util.stream.Collectors.joining;

import com.dmj.sqldsl.model.Entity;
import com.dmj.sqldsl.model.column.ValueColumn;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class TableEntityVisitor {

  @Getter
  private final List<Parameter> params;

  public TableEntityVisitor() {
    this.params = new ArrayList<>();
  }

  public String visit(Entity entity) {
    if (!entity.hasId()) {
      return visitInsert(entity);
    }
    return visitUpdate(entity);
  }

  private String visitUpdate(Entity entity) {
    String tableName = entity.getTableName();
    String sets = entity.getColumns().stream().map(column -> {
      params.add(new Parameter(column.getValue()));
      return String.format("%s = ?", column.getColumnName());
    }).collect(joining(","));
    params.add(new Parameter(entity.getId().getValue()));
    return String.format("update %s set %s where %s = ?", tableName, sets,
        entity.getId().getColumnName());
  }

  private String visitInsert(Entity entity) {
    String tableName = entity.getTableName();
    String columns = entity.getColumns().stream()
        .map(ValueColumn::getColumnName)
        .collect(joining(","));
    String values = entity.getColumns().stream()
        .map(column -> {
          params.add(new Parameter(column.getValue()));
          return "?";
        })
        .collect(joining(","));
    return String.format("insert into %s(%s) values(%s)", tableName, columns, values);
  }
}
