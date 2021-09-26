package com.dmj.sqldsl.builder.config;

import static com.dmj.sqldsl.utils.ReflectionUtils.recursiveGetFields;

import com.dmj.sqldsl.builder.column.type.LambdaType;
import com.dmj.sqldsl.builder.exception.NoColumnAnnotationException;
import com.dmj.sqldsl.builder.exception.NoTableAnnotationException;
import com.dmj.sqldsl.builder.table.EntityLayout;
import com.dmj.sqldsl.builder.table.EntityLayout.EntityField;
import com.dmj.sqldsl.model.Entity;
import com.dmj.sqldsl.model.column.Column;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class EntityConfig {

  private TableConfig tableConfig;
  private ColumnConfig columnConfig;

  @Setter
  private NameTranslator lambdaMethodTranslator;

  @Default
  private Map<Class<?>, EntityLayout> entityLayoutCacheMap = new ConcurrentHashMap<>();


  public String translateLambda(LambdaType type) {
    return lambdaMethodTranslator.translate(type.getMethodName());
  }

  public Optional<Column> getColumn(LambdaType type, String realTableName) {
    String fieldName = translateLambda(type);
    EntityLayout entityLayout = getEntityLayout(type.getTargetClass());
    if (realTableName == null) {
      return entityLayout.getColumnByFieldName(fieldName);
    }
    return entityLayout.getColumnByFieldName(fieldName, realTableName);
  }

  public List<Column> getColumns(Class<?> entityClass, String realTableName) {
    EntityLayout entityLayout = getEntityLayout(entityClass);
    if (realTableName == null) {
      return entityLayout.getAllColumns();
    }
    return entityLayout.getAllColumns(realTableName);
  }

  public String getTableName(Class<?> entityClass) {
    return getEntityLayout(entityClass).getTableName();
  }

  public Entity getTableEntity(Object entity) {
    return getEntityLayout(entity.getClass()).createEntity(entity);
  }

  public EntityLayout getEntityLayout(Class<?> entityClass) {
    return entityLayoutCacheMap.computeIfAbsent(entityClass, key -> {
      String tableName = recursiveGetTableName(key);
      List<Field> fields = recursiveGetFields(key).collect(Collectors.toList());
      EntityField id = null;
      List<EntityField> columns = new ArrayList<>();
      for (Field field : fields) {
        Optional<EntityField> fieldOptional = columnConfig.getColumnName(field)
            .map(name -> new EntityField(field, name));
        if (!fieldOptional.isPresent()) {
          continue;
        }
        if (columnConfig.isIdField(field)) {
          id = fieldOptional.get();
        } else {
          columns.add(fieldOptional.get());
        }
      }
      if (columns.isEmpty()) {
        throw new NoColumnAnnotationException(key, columnConfig.getColumnAnnotationClass());
      }
      return new EntityLayout(entityClass, tableName, id, columns);
    });
  }

  private String recursiveGetTableName(Class<?> entityClass) {
    return tableConfig.getTableName(entityClass)
        .orElseGet(() -> {
          Class<?> superclass = entityClass.getSuperclass();
          if (superclass == null) {
            throw new NoTableAnnotationException(entityClass, tableConfig.getAnnotationClass());
          }
          return recursiveGetTableName(superclass);
        });
  }
}
