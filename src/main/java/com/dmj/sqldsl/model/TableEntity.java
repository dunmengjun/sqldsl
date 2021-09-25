package com.dmj.sqldsl.model;

import static com.dmj.sqldsl.utils.ReflectionUtils.recursiveGetFields;

import com.dmj.sqldsl.builder.config.ColumnConfig;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.config.GlobalConfig;
import com.dmj.sqldsl.builder.exception.GlobalConfigNotValidException;
import com.dmj.sqldsl.builder.exception.NoColumnAnnotationException;
import com.dmj.sqldsl.model.column.ValueColumn;
import com.dmj.sqldsl.utils.EntityClassUtils;
import com.dmj.sqldsl.utils.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class TableEntity {

  private String tableName;
  private ValueColumn id;
  private List<ValueColumn> columns;

  public static TableEntity of(Object entity, EntityConfig config) {
    if (!GlobalConfig.isValid()) {
      throw new GlobalConfigNotValidException();
    }
    Class<?> entityClass = entity.getClass();
    String tableName = EntityClassUtils.getTableName(config.getTableConfig(), entityClass);
    ColumnConfig columnConfig = config.getColumnConfig();

    List<Field> fields = recursiveGetFields(entityClass).collect(Collectors.toList());
    ValueColumn id = null;
    List<ValueColumn> columns = new ArrayList<>();
    for (Field field : fields) {
      Optional<ValueColumn> valueColumn = columnConfig.getColumnName(field)
          .map(name -> {
            Object value = ReflectionUtils.getValue(field, entity);
            return new ValueColumn(value, field.getName(), name);
          });
      if (!valueColumn.isPresent()) {
        continue;
      }
      if (columnConfig.isIdField(field)) {
        id = valueColumn.get();
      } else {
        columns.add(valueColumn.get());
      }
    }
    if (columns.isEmpty()) {
      throw new NoColumnAnnotationException(entityClass, columnConfig.getColumnAnnotationClass());
    }
    return new TableEntity(tableName, id, columns);
  }

  public static TableEntity of(Object entity) {
    return of(entity, GlobalConfig.getEntityConfig());
  }

  public boolean hasId() {
    return id != null && id.getValue() != null;
  }
}
