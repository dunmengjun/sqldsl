package com.dmj.sqldsl.model.column;

import java.util.Optional;

public interface Column {

  Optional<String> getTableName();

  String getFieldName();

  String getColumnName();
}
