package com.dmj.sqldsl.builder;

import static com.dmj.sqldsl.utils.CollectionUtils.hasDuplicateIn;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.TypedLambda;
import com.dmj.sqldsl.builder.condition.ConditionBuilders;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.config.GlobalConfig;
import com.dmj.sqldsl.builder.exception.GlobalConfigNotValidException;
import com.dmj.sqldsl.builder.exception.JoinTableRepeatedlyException;
import com.dmj.sqldsl.builder.table.EntityBuilder;
import com.dmj.sqldsl.builder.table.TableBuilder;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.GroupBy;
import com.dmj.sqldsl.model.Having;
import com.dmj.sqldsl.model.JoinFlag;
import com.dmj.sqldsl.model.Order;
import com.dmj.sqldsl.model.OrderBy;
import com.dmj.sqldsl.model.SelectFrom;
import com.dmj.sqldsl.model.Where;
import com.dmj.sqldsl.model.column.Column;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DslQueryBuilder {

  private SelectBuilder selectBuilder;
  private final TableBuilder tableBuilder;
  private final List<JoinBuilder> joinBuilders;
  private final List<OrderBuilder> orderBuilders;
  private LimitBuilder limitBuilder;
  private final List<ColumnBuilder<?, ?>> groupByColumnBuilders;
  private ConditionsBuilder whereConditions;
  private ConditionsBuilder havingConditions;

  public DslQueryBuilder(SelectBuilder selectBuilder, TableBuilder tableBuilder) {
    this.selectBuilder = selectBuilder;
    this.tableBuilder = tableBuilder;
    this.joinBuilders = new ArrayList<>();
    this.orderBuilders = new ArrayList<>();
    this.groupByColumnBuilders = new ArrayList<>();
    this.havingConditions = ConditionBuilders.empty();
    this.whereConditions = ConditionBuilders.empty();
  }

  public DslQueryBuilder where(ConditionsBuilder conditionsBuilder) {
    this.whereConditions = conditionsBuilder;
    return this;
  }

  public <T, R> DslQueryBuilder orderBy(ColumnBuilder<T, R> columnBuilder, boolean isAsc) {
    this.orderBuilders.add(new OrderBuilder(columnBuilder, isAsc));
    return this;
  }

  public <T, R> DslQueryBuilder orderBy(ColumnLambda<T, R> function, boolean isAsc) {
    this.orderBuilders.add(new OrderBuilder(function.getColumnBuilder(), isAsc));
    return this;
  }

  @SafeVarargs
  public final <T, R> DslQueryBuilder groupBy(ColumnLambda<T, R>... functions) {
    return groupBy(Arrays.asList(functions));
  }

  public <T> DslQueryBuilder groupBy(Collection<ColumnLambda<T, ?>> functions) {
    List<ColumnBuilder<?, ?>> columnBuilders = functions.stream()
        .map(TypedLambda::getColumnBuilder)
        .collect(Collectors.toList());
    this.groupByColumnBuilders.addAll(columnBuilders);
    return this;
  }

  @SafeVarargs
  public final <T, R> DslQueryBuilder groupBy(ColumnBuilder<T, R>... columnBuilders) {
    this.groupByColumnBuilders.addAll(asList(columnBuilders));
    return this;
  }

  public DslQueryBuilder having(ConditionsBuilder havingConditions) {
    this.havingConditions = havingConditions;
    return this;
  }

  public DslQueryBuilder limit(int offset, int size) {
    this.limitBuilder = new LimitBuilder(offset, size);
    return this;
  }

  public DslQueryBuilder limit(int size) {
    this.limitBuilder = new LimitBuilder(0, size);
    return this;
  }


  public DslQueryBuilder leftJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.left, new EntityBuilder(entityClass), conditionsBuilder));
    return this;
  }

  public DslQueryBuilder leftJoin(TableBuilder tableBuilder, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(JoinFlag.left, tableBuilder, conditionsBuilder));
    return this;
  }

  public DslQueryBuilder rightJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.right, new EntityBuilder(entityClass), conditionsBuilder));
    return this;
  }

  public DslQueryBuilder rightJoin(TableBuilder tableBuilder, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.right, tableBuilder, conditionsBuilder));
    return this;
  }

  public DslQueryBuilder innerJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.inner, new EntityBuilder(entityClass), conditionsBuilder));
    return this;
  }

  public DslQueryBuilder innerJoin(TableBuilder tableBuilder, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.inner, tableBuilder, conditionsBuilder));
    return this;
  }

  public DslQuery toQuery(EntityConfig config) {
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
    List<Order> orders = orderBuilders.stream().distinct()
        .map(orderBuilder -> orderBuilder.build(config))
        .collect(toList());
    List<Column> groupByColumns = groupByColumnBuilders.stream()
        .map(columnBuilder -> columnBuilder.build(config))
        .collect(toList());
    DslQuery.DslQueryBuilder builder = DslQuery.builder()
        .selectFrom(selectFrom)
        .where(new Where(whereConditions.build(config)))
        .groupBy(new GroupBy(groupByColumns))
        .having(new Having(havingConditions.build(config)))
        .orderBy(new OrderBy(orders));
    if (limitBuilder != null) {
      builder.limit(limitBuilder.build());
    }
    return builder.build();
  }

  public DslQuery toQuery() {
    if (!GlobalConfig.isValid()) {
      throw new GlobalConfigNotValidException();
    }
    return this.toQuery(GlobalConfig.entityConfig);
  }


  public void setSelectBuilder(SelectBuilder selectBuilder) {
    this.selectBuilder = selectBuilder;
  }

  public SelectBuilder getSelectBuilder() {
    return this.selectBuilder;
  }
}
