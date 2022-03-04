package com.dmj.sqldsl.builder.table;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.model.ModifiedFlag;
import java.util.List;

public interface Modified {

  default ModifiedFlag getModifiedFlag() {
    return ModifiedFlag.UPDATE;
  }

  default List<ColumnLambda<?, ?>> getForceUpdatedColumns() {
    return null;
  }

  default <T, R> void setForceUpdatedColumns(ColumnLambda<T, R>... columns) {
  }
}
