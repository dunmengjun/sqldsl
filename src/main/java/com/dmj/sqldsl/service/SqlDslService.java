package com.dmj.sqldsl.service;

import static com.dmj.sqldsl.builder.column.ColumnBuilders.all;
import static com.dmj.sqldsl.builder.column.ColumnBuilders.count;
import static com.dmj.sqldsl.builder.config.GlobalConfig.getEntityConfig;
import static java.util.Collections.emptyList;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.SelectBuilder;
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
    return select(new Wrapper<>(entityClass));
  }

  public <T> List<T> select(Wrapper<T> wrapper) {
    DslQueryBuilder queryBuilder = wrapper.toQueryBuilder();
    return select(queryBuilder, wrapper.getEntityClass());
  }

  public <T> List<T> select(DslQueryBuilder queryBuilder, Class<T> resultClass) {
    return executor.execute(queryBuilder.toQuery(), resultClass);
  }

  public <T> Page<T> select(PageRequest request, Class<T> entityClass) {
    return select(request, new Wrapper<>(entityClass));
  }

  public <T> Page<T> select(PageRequest request, Wrapper<T> wrapper) {
    return select(wrapper.toQueryBuilder(), request, wrapper.getEntityClass());
  }

  public <T> Page<T> select(DslQueryBuilder queryBuilder, PageRequest request,
      Class<T> resultClass) {
    SelectBuilder selectBuilder = queryBuilder.getSelectBuilder();
    queryBuilder.setSelectBuilder(new SelectBuilder().select(count(all())));
    long total = executor.execute(queryBuilder.toQuery(), Long.class).get(0);

    if (total == 0) {
      return new Page<>(request, 0, emptyList());
    }

    queryBuilder.setSelectBuilder(selectBuilder);
    DslQuery dataQuery = queryBuilder
        .limit(request.getNumber() - 1, request.getSize())
        .toQuery();
    List<T> resultList = executor.execute(dataQuery, resultClass);

    return new Page<>(request, total, resultList);
  }

  public <T> int save(T entity) {
    return executor.save(getEntityConfig().getTableEntity(entity));
  }
}
