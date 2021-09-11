package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.NoColumnAnnotationException;
import com.dmj.sqldsl.builder.exception.NoTableAnnotationException;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;

public class EntityColumnsBuilder implements ColumnsBuilder {

    private final Class<?> entityClass;

    public EntityColumnsBuilder(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<Column> build(EntityConfig config) {
        Class<? extends Annotation> tableClass = config.getTableAnnotation().getAnnotationClass();
        Class<? extends Annotation> columnClass = config.getColumnAnnotation().getAnnotationClass();
        if (!entityClass.isAnnotationPresent(tableClass)) {
            throw new NoTableAnnotationException(entityClass, tableClass);
        }
        String tableNameAttribute = config.getTableAnnotation().getTableNameAttribute();
        String tableName = invokeMethod(tableNameAttribute, entityClass.getAnnotation(tableClass));
        if (StringUtils.isBlank(tableName)) {
            tableName = entityClass.getSimpleName();
        }
        String finalTableName = tableName;
        String attribute = config.getColumnAnnotation().getColumnNameAttribute();
        List<Column> columns = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(columnClass))
                .map(field -> {
                    String columnName = invokeMethod(attribute, field.getAnnotation(columnClass));
                    if (StringUtils.isBlank(columnName)) {
                        columnName = field.getName();
                    }
                    return new SimpleColumn(finalTableName, columnName);
                })
                .collect(Collectors.toList());
        if (columns.isEmpty()) {
            throw new NoColumnAnnotationException(entityClass, columnClass);
        }
        return columns;
    }
}
