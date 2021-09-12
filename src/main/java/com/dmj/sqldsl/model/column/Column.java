package com.dmj.sqldsl.model.column;

import java.util.Optional;

public interface Column {

  String getTableName();

  String getName();

  Optional<String> getAlias();
}
