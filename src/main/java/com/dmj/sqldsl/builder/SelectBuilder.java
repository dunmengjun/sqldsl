package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.ColumnsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.table.EntityTablesBuilder;
import com.dmj.sqldsl.model.column.Column;

import java.util.Arrays;
import java.util.List;

public class SelectBuilder {

    private final ColumnsBuilder columnsBuilder;

    public SelectBuilder(ColumnsBuilder columnsBuilder) {
        this.columnsBuilder = columnsBuilder;
    }

    public FromBuilder from(Class<?>... entityClasses) {
        return new FromBuilder(this, new EntityTablesBuilder(Arrays.asList(entityClasses)));
    }

    protected List<Column> build(EntityConfig config) {
        return columnsBuilder.build(config);
    }
}
