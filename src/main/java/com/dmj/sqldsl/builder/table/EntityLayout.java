package com.dmj.sqldsl.builder.table;

import static com.dmj.sqldsl.utils.ReflectionUtils.getValue;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.model.Entity;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.model.column.ValueColumn;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class EntityLayout {

  private Class<?> entityClass;
  private String tableName;
  private EntityField id;
  private List<EntityField> columns;

  @AllArgsConstructor
  @Getter
  @EqualsAndHashCode
  public static class EntityField {

    private Field field;
    private String columnName;

    public ValueColumn createValueColumn(Object entity) {
      return new ValueColumn(getValue(field, entity), field.getName(), columnName);
    }

    public String getFieldName() {
      return field.getName();
    }

    public boolean isNamed(String fieldName) {
      return field.getName().equals(fieldName);
    }
  }

  public Entity createEntity(Object entity) {
    ValueColumn valueId = null;
    if (id != null) {
      valueId = id.createValueColumn(entity);
    }
    List<ValueColumn> valueColumns = columns.stream()
        .map(entityField -> entityField.createValueColumn(entity))
        .collect(toList());
    return new Entity(tableName, valueId, valueColumns);
  }

  public List<Column> getAllColumns() {
    return getAllColumns(tableName);
  }

  public List<Column> getAllColumns(String realTableName) {
    List<Column> columnList = columns.stream().map(entityField ->
            SimpleColumn.builder()
                .tableName(realTableName)
                .fieldName(entityField.getFieldName())
                .columnName(entityField.getColumnName())
                .build())
        .collect(toList());
    columnList.add(
        SimpleColumn.builder()
            .tableName(realTableName)
            .fieldName(id.getFieldName())
            .columnName(id.getColumnName())
            .build());
    return columnList;
  }

  public Optional<Column> getColumnByFieldName(String fieldName) {
    return getColumnByFieldName(fieldName, tableName);
  }

  public Optional<Column> getColumnByFieldName(String fieldName, String realTableName) {
    if (id.isNamed(fieldName)) {
      return Optional.of(SimpleColumn.builder()
          .tableName(realTableName)
          .columnName(id.getColumnName())
          .fieldName(id.getFieldName())
          .build());
    }
    return columns.stream().filter(column -> column.isNamed(fieldName))
        .map(column ->
            (Column) SimpleColumn.builder()
                .tableName(realTableName)
                .fieldName(column.getFieldName())
                .columnName(column.getColumnName())
                .build())
        .findAny();
  }
}
