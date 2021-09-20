package com.dmj.sqldsl.builder;

import static com.dmj.sqldsl.utils.CollectionUtils.asModifiableList;
import static com.dmj.sqldsl.utils.CollectionUtils.hasDuplicateIn;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.JoinTableRepeatedlyException;
import com.dmj.sqldsl.builder.table.EntityTableBuilder;
import com.dmj.sqldsl.builder.table.TableBuilder;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.JoinFlag;
import com.dmj.sqldsl.model.SelectFrom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FromBuilder implements DslQueryBuilder {

  private final SelectBuilder selectBuilder;
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

  public LimitBuilder limit(int offset, int size) {
    return new FromLimitBuilder(this, offset, size);
  }

  public LimitBuilder limit(int size) {
    return new FromLimitBuilder(this, 0, size);
  }

  public FromBuilder leftJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.left, new EntityTableBuilder(entityClass), conditionsBuilder));
    return this;
  }

  public FromBuilder leftJoin(TableBuilder tableBuilder, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(JoinFlag.left, tableBuilder, conditionsBuilder));
    return this;
  }

  public FromBuilder rightJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.right, new EntityTableBuilder(entityClass), conditionsBuilder));
    return this;
  }


  public FromBuilder rightJoin(TableBuilder tableBuilder, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.right, tableBuilder, conditionsBuilder));
    return this;
  }

  public FromBuilder innerJoin(Class<?> entityClass, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.inner, new EntityTableBuilder(entityClass), conditionsBuilder));
    return this;
  }

  public FromBuilder innerJoin(TableBuilder tableBuilder, ConditionsBuilder conditionsBuilder) {
    this.joinBuilders.add(new JoinBuilder(
        JoinFlag.inner, tableBuilder, conditionsBuilder));
    return this;
  }

  @SafeVarargs
  public final <T, R> GroupByBuilder groupBy(ColumnLambda<T, R>... functions) {
    return new FromGroupByBuilder(this, new NormalColumnsBuilder(Arrays.asList(functions)));
  }

  public <T, R> OrderByBuilder orderBy(ColumnLambda<T, R> function, boolean isAsc) {
    return new FromOrderByBuilder(this, asModifiableList(new OrderBuilder(function, isAsc)));
  }

  protected DslQuery.DslQueryBuilder build(EntityConfig config) {
    if (hasDuplicateIn(joinBuilders, JoinBuilder::getTableBuilder)) {
      throw new JoinTableRepeatedlyException();
    }
    SelectFrom selectFrom = SelectFrom.builder()
        .columns(selectBuilder.build(config))
        .table(tableBuilder.build(config))
        .joins(joinBuilders.stream()
            .map(joinBuilder -> joinBuilder.build(config))
            .collect(toList()))
        .build();
    return DslQuery.builder().selectFrom(selectFrom);
  }

  public DslQuery toQuery(EntityConfig config) {
    return this.build(config).build();
  }
}
