package com.dmj.sqldsl.builder.table;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.Table;
import java.util.List;

public interface TablesBuilder {

  List<Table> build(EntityConfig config);
}
