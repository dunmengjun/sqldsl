package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.column.*;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.table.EntityTablesBuilder;
import com.dmj.sqldsl.model.column.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class SelectBuilder {

    private final List<ColumnsBuilder> columnsBuilders;

    public SelectBuilder(ColumnsBuilder columnsBuilder) {
        this.columnsBuilders = new ArrayList<>();
        this.columnsBuilders.add(columnsBuilder);
    }

    public SelectBuilder selectAll(Class<?> entityClass) {
        this.columnsBuilders.add(new EntityColumnsBuilder(entityClass));
        return this;
    }

    @SafeVarargs
    public final <T, R> SelectBuilder select(ColumnFunction<T, R>... functions) {
        this.columnsBuilders.add(new FunctionColumnsBuilder(asList(functions)));
        return this;
    }

    public <T, R, O, K> SelectBuilder selectAs(ColumnFunction<T, R> column, ColumnFunction<O, K> alias) {
        this.columnsBuilders.add(new FunctionAliasColumnsBuilder(column, alias));
        return this;
    }

    public FromBuilder from(Class<?>... entityClasses) {
        return new FromBuilder(this, new EntityTablesBuilder(asList(entityClasses)));
    }

    protected List<Column> build(EntityConfig config) {
        return this.columnsBuilders.stream()
                .flatMap(x -> x.build(config).stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
