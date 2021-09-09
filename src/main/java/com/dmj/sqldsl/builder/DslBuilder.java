package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.ColumnFunction;
import com.dmj.sqldsl.builder.column.EntityColumnsBuilder;
import com.dmj.sqldsl.builder.column.FunctionColumnsBuilder;

import java.util.Arrays;

public class DslBuilder {

    public SelectBuilder select(Class<?> entityClass) {
        return new SelectBuilder(new EntityColumnsBuilder(entityClass));
    }

    public SelectBuilder select(ColumnFunction<?>... functions) {
        return new SelectBuilder(new FunctionColumnsBuilder(Arrays.asList(functions)));
    }
}
