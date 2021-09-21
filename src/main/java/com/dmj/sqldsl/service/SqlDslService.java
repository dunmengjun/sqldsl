package com.dmj.sqldsl.service;

import static com.dmj.sqldsl.builder.column.ColumnBuilders.all;
import static com.dmj.sqldsl.builder.column.ColumnBuilders.count;
import static java.util.Collections.emptyList;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.SelectBuilder;
import com.dmj.sqldsl.builder.condition.ConditionBuilders;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.executor.SqlDslExecutor;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.service.page.Page;
import com.dmj.sqldsl.service.page.PageRequest;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SqlDslService {

  private SqlDslExecutor executor;

  public <T> List<T> select(Class<T> entityClass) {
    return select(ConditionBuilders.empty(), entityClass);
  }

  public <T> List<T> select(ConditionsBuilder conditions, Class<T> entityClass) {
    DslQueryBuilder queryBuilder = DslQueryBuilder.selectAll(entityClass)
        .from(entityClass)
        .where(conditions);
    return select(queryBuilder, entityClass);
  }

  public <T> List<T> select(DslQueryBuilder queryBuilder, Class<T> resultClass) {
    return executor.execute(queryBuilder.toQuery(), resultClass);
  }

  public <T> Page<T> select(PageRequest request, Class<T> entityClass) {
    return select(request, ConditionBuilders.empty(), entityClass);
  }

  public <T> Page<T> select(PageRequest request, ConditionsBuilder conditions,
      Class<T> entityClass) {
    DslQueryBuilder queryBuilder = DslQueryBuilder
        .selectAll(entityClass)
        .from(entityClass)
        .where(conditions);
    return select(queryBuilder, request, entityClass);
  }

  public <T> Page<T> select(DslQueryBuilder queryBuilder, PageRequest request,
      Class<T> resultClass) {
    SelectBuilder selectBuilder = queryBuilder.getSelectBuilder();
    queryBuilder.setSelectBuilder(DslQueryBuilder.select(count(all())));
    long total = executor.execute(queryBuilder.toQuery(), Long.class).get(0);

    if (total == 0) {
      return new Page<>(request, 0, emptyList());
    }

    queryBuilder.setSelectBuilder(selectBuilder);
    DslQuery dataQuery = queryBuilder.limit(request.getPageNumber(), request.getSize()).toQuery();
    List<T> resultList = executor.execute(dataQuery, resultClass);

    return new Page<>(request, total, resultList);
  }
}
