package com.dmj.sqldsl.builder.table;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.table.Table;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface TableBuilder {

  AtomicInteger NUMBER = new AtomicInteger(0);

  Table buildTable(EntityConfig config);

  List<Column> buildColumns(EntityConfig config);

  static String getAlias(String tableName) {
    return tableName + NUMBER.getAndIncrement();
  }
}
