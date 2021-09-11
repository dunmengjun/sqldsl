package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.config.GlobalConfig;
import com.dmj.sqldsl.builder.exception.GlobalConfigNotValidException;
import com.dmj.sqldsl.model.DslQuery;

public interface ToDslQuery {

  DslQuery toQuery(EntityConfig config);

  default DslQuery toQuery() {
    if (!GlobalConfig.isValid()) {
      throw new GlobalConfigNotValidException();
    }
    return toQuery(GlobalConfig.entityConfig);
  }
}
