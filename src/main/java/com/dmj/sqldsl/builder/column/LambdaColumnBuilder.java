package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.exception.NoColumnAnnotationException;
import com.dmj.sqldsl.exception.NoTableAnnotationException;
import com.dmj.sqldsl.exception.ReflectionException;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.utils.ReflectionUtils;
import com.dmj.sqldsl.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;

import static com.dmj.sqldsl.utils.ReflectionUtils.invokeMethod;

public class LambdaColumnBuilder implements ColumnBuilder {

    private final SerializedLambda lambda;

    public LambdaColumnBuilder(SerializedLambda lambda) {
        this.lambda = lambda;
    }

    @Override
    public Column build(EntityConfig config) {
        String implClass = lambda.getImplClass().replace("/", ".");
        String implMethodName = lambda.getImplMethodName();
        String fieldName = config.getTranslator().translate(implMethodName);
        try {
            Class<?> entityClass = Class.forName(implClass);
            return getColumn(entityClass, fieldName, config);
        } catch (ClassNotFoundException e) {
            throw new ReflectionException(e);
        }
    }

    private SimpleColumn getColumn(Class<?> entityClass, String fieldName, EntityConfig config) {
        Class<? extends Annotation> tableClass = config.getTableAnnotation().getAnnotationClass();
        if (!entityClass.isAnnotationPresent(tableClass)) {
            throw new NoTableAnnotationException(entityClass, tableClass);
        }
        String tableNameAttribute = config.getTableAnnotation().getTableNameAttribute();
        String tableName = invokeMethod(tableNameAttribute, entityClass.getAnnotation(tableClass));
        if (StringUtils.isBlank(tableName)) {
            tableName = entityClass.getSimpleName();
        }
        Field field = ReflectionUtils.getField(entityClass, fieldName);
        Class<? extends Annotation> columnClass = config.getColumnAnnotation().getAnnotationClass();
        if (!field.isAnnotationPresent(columnClass)) {
            throw new NoColumnAnnotationException(entityClass, columnClass);
        }
        String attribute = config.getColumnAnnotation().getColumnNameAttribute();
        String columnName = invokeMethod(attribute, field.getAnnotation(columnClass));
        if (StringUtils.isBlank(columnName)) {
            columnName = field.getName();
        }
        return new SimpleColumn(tableName, columnName, field.getType());
    }
}
