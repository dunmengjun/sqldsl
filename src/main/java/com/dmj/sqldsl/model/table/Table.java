package com.dmj.sqldsl.model.table;

import com.dmj.sqldsl.model.column.Column;
import java.util.List;

public interface Table {

  String getName();

  List<Column> getColumns();
}
