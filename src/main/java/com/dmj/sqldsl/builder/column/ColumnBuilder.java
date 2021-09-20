package com.dmj.sqldsl.builder.column;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;

public interface ColumnBuilder<T, R> {

  Column build(EntityConfig config);
}
