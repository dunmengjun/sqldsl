package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;

import java.util.List;

public interface ColumnsBuilder {
    List<Column> build(EntityConfig config);
}
