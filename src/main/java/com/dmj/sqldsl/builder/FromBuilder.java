package com.dmj.sqldsl.builder;

import static com.dmj.sqldsl.utils.CollectionUtils.hasDuplicateIn;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.ColumnFunction;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.JoinTableRepeatedlyException;
import com.dmj.sqldsl.builder.table.TablesBuilder;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.JoinFlag;
import com.dmj.sqldsl.model.SelectFrom;
import com.dmj.sqldsl.model.condition.Conditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FromBuilder implements ToDslQuery {

  private final SelectBuilder selectBuilder;
  private final TablesBuilder tablesBuilder;
  private final List<JoinBuilder> joinBuilders;

  public FromBuilder(SelectBuilder selectBuilder, TablesBuilder tablesBuilder) {
    this.selectBuilder = selectBuilder;
    this.tablesBuilder = tablesBuilder;
    this.joinBuilders = new ArrayList<>();
  }

  public WhereBuilder where(ConditionsBuilder conditionsBuilder) {
    return new WhereBuilder(this, conditionsBuilder);
  }

  public FromBuilder leftJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(JoinFlag.left, entityClass, conditionsBuilder));
    return this;
  }

  public FromBuilder rightJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(JoinFlag.right, entityClass, conditionsBuilder));
    return this;
  }

  public FromBuilder innerJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(JoinFlag.inner, entityClass, conditionsBuilder));
    return this;
  }

  @SafeVarargs
  public final <T, R> GroupByBuilder groupBy(ColumnFunction<T, R>... functions) {
    return new GroupByBuilder(this, Arrays.asList(functions));
  }

  protected SelectFrom build(EntityConfig config) {
    if (hasDuplicateIn(joinBuilders, JoinBuilder::getEntityClass)) {
      throw new JoinTableRepeatedlyException();
    }
    return SelectFrom.builder()
        .columns(selectBuilder.build(config))
        .tables(tablesBuilder.build(config))
        .joins(joinBuilders.stream()
            .map(joinBuilder -> joinBuilder.build(config))
            .collect(toList()))
        .build();
  }

  public DslQuery toQuery(EntityConfig config) {
    return DslQuery.builder()
        .selectFrom(this.build(config))
        .conditions(Conditions.empty())
        .build();
  }
}
