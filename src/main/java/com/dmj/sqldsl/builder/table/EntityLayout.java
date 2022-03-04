package com.dmj.sqldsl.builder.table;

import static com.dmj.sqldsl.builder.config.GlobalConfig.getEntityConfig;
import static com.dmj.sqldsl.utils.ReflectionUtils.getValue;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.exception.NoIdColumnException;
import com.dmj.sqldsl.model.Entity;
import com.dmj.sqldsl.model.ModifiedFlag;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.model.column.ValueColumn;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    ModifiedFlag flag = ModifiedFlag.UPDATE;
    List<String> forceUpdatedColumns = emptyList();
    if (id != null) {
      valueId = id.createValueColumn(entity);
      if (entity instanceof Modified) {
        Modified modified = (Modified) entity;
        flag = modified.getModifiedFlag() == null ?
            ModifiedFlag.UPDATE : modified.getModifiedFlag();
        if (valueId.isNullValue()) {
          flag = ModifiedFlag.INSERT;
        }
        if (flag == ModifiedFlag.UPDATE) {
          List<ColumnLambda<?, ?>> columnLambdas = modified.getForceUpdatedColumns();
          if (columnLambdas != null) {
            forceUpdatedColumns = columnLambdas.stream()
                .map(ColumnLambda::getLambdaType)
                .map(type -> getEntityConfig().translateLambda(type))
                .collect(Collectors.toList());
          }
        }
      } else {
        if (valueId.isNullValue()) {
          flag = ModifiedFlag.INSERT;
        }
      }
    } else {
      if (entity instanceof Modified) {
        Modified modified = (Modified) entity;
        flag = modified.getModifiedFlag() == null ?
            ModifiedFlag.UPDATE : modified.getModifiedFlag();
        if (flag != ModifiedFlag.INSERT) {
          throw new NoIdColumnException();
        }
      } else {
        flag = ModifiedFlag.INSERT;
      }
    }
    List<String> finalForceUpdatedColumns = forceUpdatedColumns;
    List<ValueColumn> valueColumns = columns.stream()
        .map(entityField -> entityField.createValueColumn(entity))
        .filter(valueColumn -> finalForceUpdatedColumns.contains(valueColumn.getFieldName())
            || !valueColumn.isNullValue())
        .collect(toList());
    return new Entity(tableName, valueId, valueColumns, flag);
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
