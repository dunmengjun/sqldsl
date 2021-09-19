package com.dmj.sqldsl.builder.table;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.Table;

public interface TableBuilder {

  Table build(EntityConfig config);
}
