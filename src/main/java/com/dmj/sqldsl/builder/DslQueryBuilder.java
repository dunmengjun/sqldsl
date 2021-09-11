package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.ColumnFunction;
import com.dmj.sqldsl.builder.column.EntityColumnsBuilder;
import com.dmj.sqldsl.builder.column.FunctionAliasColumnsBuilder;
import com.dmj.sqldsl.builder.column.FunctionColumnsBuilder;

import java.util.Arrays;

public class DslQueryBuilder {

    public static SelectBuilder selectAll(Class<?> entityClass) {
        return new SelectBuilder(new EntityColumnsBuilder(entityClass));
    }

    @SafeVarargs
    public static <T, R> SelectBuilder select(ColumnFunction<T, R>... functions) {
        return new SelectBuilder(new FunctionColumnsBuilder(Arrays.asList(functions)));
    }

    public static <T, R, O, K> SelectBuilder selectAs(ColumnFunction<T, R> column, ColumnFunction<O, K> alias) {
        return new SelectBuilder(new FunctionAliasColumnsBuilder(column, alias));
    }
}
