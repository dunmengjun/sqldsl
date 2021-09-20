package com.dmj.sqldsl.builder;

import static com.dmj.sqldsl.utils.CollectionUtils.asModifiableList;
import static com.dmj.sqldsl.utils.CollectionUtils.hasDuplicateIn;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.ColumnBuilder;
import com.dmj.sqldsl.builder.column.NormalColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.column.type.SerializableLambda;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.JoinTableRepeatedlyException;
import com.dmj.sqldsl.builder.table.EntityBuilder;
import com.dmj.sqldsl.builder.table.TableBuilder;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.JoinFlag;
import com.dmj.sqldsl.model.SelectFrom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

  @SafeVarargs
  public final <T, R> GroupByBuilder groupBy(ColumnLambda<T, R>... functions) {
    List<ColumnBuilder<?, ?>> columnBuilders = Arrays.stream(functions)
        .map(SerializableLambda::getColumnBuilder)
        .collect(Collectors.toList());
    return new FromGroupByBuilder(this, new NormalColumnsBuilder(columnBuilders));
  }

  @SafeVarargs
  public final <T, R> GroupByBuilder groupBy(ColumnBuilder<T, R>... columnBuilders) {
    return new FromGroupByBuilder(this,
        new NormalColumnsBuilder(asList(columnBuilders)));
  }

  public <T, R> OrderByBuilder orderBy(ColumnLambda<T, R> function, boolean isAsc) {
    return new FromOrderByBuilder(this,
        asModifiableList(new OrderBuilder(function.getColumnBuilder(), isAsc)));
  }

  public <T, R> OrderByBuilder orderBy(ColumnBuilder<T, R> columnBuilder, boolean isAsc) {
    return new FromOrderByBuilder(this,
        asModifiableList(new OrderBuilder(columnBuilder, isAsc)));
  }

  protected DslQuery.DslQueryBuilder build(EntityConfig config) {
    if (hasDuplicateIn(joinBuilders, JoinBuilder::getTableBuilder)) {
      throw new JoinTableRepeatedlyException();
    }
    SelectFrom selectFrom = SelectFrom.builder()
        .columns(selectBuilder.build(config))
        .table(tableBuilder.buildTable(config))
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
