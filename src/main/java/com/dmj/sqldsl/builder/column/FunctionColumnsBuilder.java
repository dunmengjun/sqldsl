package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;

import java.util.List;

public class FunctionColumnsBuilder implements ColumnsBuilder{

    private List<ColumnFunction<?>> functions;

    public FunctionColumnsBuilder(List<ColumnFunction<?>> functions) {
        this.functions = functions;
    }

    @Override
    public List<Column> build(EntityConfig config) {
        return null;
    }
}
