package com.dmj.sqldsl.builder;

import static com.dmj.sqldsl.utils.CollectionUtils.hasDuplicateIn;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.JoinTableRepeatedlyException;
import com.dmj.sqldsl.builder.table.EntityBuilder;
import com.dmj.sqldsl.builder.table.TableBuilder;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.JoinFlag;
import com.dmj.sqldsl.model.SelectFrom;
import java.util.ArrayList;
import java.util.List;

public class FromBuilder extends AbstractDslQueryBuilder {

  private SelectBuilder selectBuilder;
  private final TableBuilder tableBuilder;
  private final List<JoinBuilder> joinBuilders;

  public FromBuilder(SelectBuilder selectBuilder, TableBuilder tableBuilder) {
    this.selectBuilder = selectBuilder;
    this.tableBuilder = tableBuilder;
    this.joinBuilders = new ArrayList<>();
  }

  public WhereBuilder where(ConditionsBuilder conditionsBuilder) {
    return new WhereBuilder(this, conditionsBuilder);
  }

  public FromBuilder leftJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.left, new EntityBuilder(entityClass), conditionsBuilder));
    return this;
  }

  public FromBuilder leftJoin(TableBuilder tableBuilder, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(JoinFlag.left, tableBuilder, conditionsBuilder));
    return this;
  }

  public FromBuilder rightJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.right, new EntityBuilder(entityClass), conditionsBuilder));
    return this;
  }

  public FromBuilder rightJoin(TableBuilder tableBuilder, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.right, tableBuilder, conditionsBuilder));
    return this;
  }

  public FromBuilder innerJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.inner, new EntityBuilder(entityClass), conditionsBuilder));
    return this;
  }

  public FromBuilder innerJoin(TableBuilder tableBuilder, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.inner, tableBuilder, conditionsBuilder));
    return this;
  }

  @Override
  public DslQuery.DslQueryBuilder buildDslQueryBuilder(EntityConfig config) {
    if (hasDuplicateIn(joinBuilders, JoinBuilder::getTableBuilder)) {
      throw new JoinTableRepeatedlyException();
    }
    SelectFrom selectFrom = SelectFrom.builder()
        .columns(this.selectBuilder.build(config))
        .table(tableBuilder.buildTable(config))
        .joins(joinBuilders.stream()
            .map(joinBuilder -> joinBuilder.build(config))
            .collect(toList()))
        .build();
    return DslQuery.builder().selectFrom(selectFrom);
  }

  @Override
  public void setSelectBuilder(SelectBuilder selectBuilder) {
    this.selectBuilder = selectBuilder;
  }

  @Override
  public SelectBuilder getSelectBuilder() {
    return this.selectBuilder;
  }
}
