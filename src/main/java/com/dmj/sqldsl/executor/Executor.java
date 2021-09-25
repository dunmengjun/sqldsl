package com.dmj.sqldsl.executor;

import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.TableEntity;
import java.util.List;

public interface Executor {

  <T> List<T> execute(DslQuery query, Class<T> resultClass);

  int save(TableEntity entity);
}
