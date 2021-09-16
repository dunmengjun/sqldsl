package com.dmj.sqldsl.builder;

import static com.dmj.sqldsl.utils.CollectionUtils.asModifiableList;
import static com.dmj.sqldsl.utils.CollectionUtils.hasDuplicateIn;
import static java.util.stream.Collectors.toList;

import com.dmj.sqldsl.builder.column.FunctionColumnsBuilder;
import com.dmj.sqldsl.builder.column.type.ColumnFunction;
import com.dmj.sqldsl.builder.condition.ConditionalExpression;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.exception.JoinTableRepeatedlyException;
import com.dmj.sqldsl.builder.table.TablesBuilder;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.JoinFlag;
import com.dmj.sqldsl.model.SelectFrom;
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

  public WhereBuilder where(ConditionalExpression conditionalExpression) {
    return new WhereBuilder(this, conditionalExpression);
  }

  public LimitBuilder limit(int offset, int size) {
    return new FromLimitBuilder(this, offset, size);
  }

  public LimitBuilder limit(int size) {
    return new FromLimitBuilder(this, 0, size);
  }

  public FromBuilder leftJoin(Class<?> entityClass, ConditionalExpression conditionalExpression) {
    this.joinBuilders.add(new JoinBuilder(JoinFlag.left, entityClass, conditionalExpression));
    return this;
  }

  public FromBuilder rightJoin(Class<?> entityClass, ConditionalExpression conditionalExpression) {
    this.joinBuilders.add(new JoinBuilder(JoinFlag.right, entityClass, conditionalExpression));
    return this;
  }

  public FromBuilder innerJoin(Class<?> entityClass, ConditionalExpression conditionalExpression) {
    this.joinBuilders.add(new JoinBuilder(JoinFlag.inner, entityClass, conditionalExpression));
    return this;
  }

  @SafeVarargs
  public final <T, R> GroupByBuilder groupBy(ColumnFunction<T, R>... functions) {
    return new FromGroupByBuilder(this, new FunctionColumnsBuilder(Arrays.asList(functions)));
  }

  public <T, R> OrderByBuilder orderBy(ColumnFunction<T, R> function, boolean isAsc) {
    return new FromOrderByBuilder(this, asModifiableList(new OrderBuilder(function, isAsc)));
  }

  protected DslQuery.DslQueryBuilder build(EntityConfig config) {
    if (hasDuplicateIn(joinBuilders, JoinBuilder::getEntityClass)) {
      throw new JoinTableRepeatedlyException();
    }
    SelectFrom selectFrom = SelectFrom.builder()
        .columns(selectBuilder.build(config))
        .tables(tablesBuilder.build(config))
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
