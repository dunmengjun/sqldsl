package com.dmj.sqldsl.executor.visitor;

import static java.util.stream.Collectors.joining;

import com.dmj.sqldsl.builder.Limit;
import com.dmj.sqldsl.executor.exception.UnsupportedJoinFlagException;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Join;
import com.dmj.sqldsl.model.JoinFlag;
import com.dmj.sqldsl.model.SelectFrom;
import com.dmj.sqldsl.model.SimpleTable;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.model.column.ValueColumn;
import com.dmj.sqldsl.model.condition.And;
import com.dmj.sqldsl.model.condition.Condition;
import com.dmj.sqldsl.model.condition.ConditionElement;
import com.dmj.sqldsl.model.condition.Conditions;
import com.dmj.sqldsl.model.condition.Or;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class StandardModelVisitor extends ModelVisitor {

  private final List<Parameter> params;

  public StandardModelVisitor() {
    this.params = new ArrayList<>();
  }

  public String visit(DslQuery query) {
    String selectFromWhere = visitSelectFromWhere(query);
    return query.getLimit()
        .map(limit -> visitLimit(selectFromWhere, limit))
        .orElse(selectFromWhere);
  }

  protected String visit(Limit limit) {
    return String.format(" limit %s,%s", limit.getOffset(), limit.getSize());
  }

  protected String visit(Join join) {
    return String.format("%s %s on %s", visit(join.getFlag()),
        visit(join.getTable()), visitFirstConditions(join.getConditions()));
  }

  protected String visit(JoinFlag flag) {
    switch (flag) {
      case left:
        return "left join";
      case right:
        return "right join";
      case inner:
        return "inner join";
      default:
        throw new UnsupportedJoinFlagException(flag);
    }
  }

  protected String visit(SelectFrom selectFrom) {
    String columnsSqlString = selectFrom.getColumns().stream()
        .map(this::visit)
        .collect(joining(", "));
    String tablesSqlString = selectFrom.getTables().stream()
        .map(this::visit)
        .collect(joining(","));
    String joinSqlString = selectFrom.getJoins()
        .map(x -> x.stream().map(this::visit).collect(joining(" ", " ", "")))
        .orElse("");
    return String.format("select %s from %s%s", columnsSqlString, tablesSqlString, joinSqlString);
  }

  @Override
  protected String visit(Conditions conditions) {
    List<ConditionElement> conditionElements = conditions.getConditionElements();
    return conditionElements.stream()
        .map(this::visit)
        .collect(joining(" ", "(", ")"));
  }

  @Override
  protected String visit(Condition condition) {
    return String.format("%s = %s", visit(condition.getLeft()), visit(condition.getRight()));
  }

  @Override
  protected String visit(And and) {
    return "and";
  }

  @Override
  protected String visit(Or or) {
    return "or";
  }


  protected String visit(SimpleColumn column) {
    String name = column.getTableName()
        .map(tableName -> String.format("%s.%s", tableName, column.getName()))
        .orElse(column.getName());
    return column.getAlias()
        .map(alias -> String.format("%s as %s", name, alias))
        .orElse(name);
  }

  @Override
  protected String visit(ValueColumn column) {
    params.add(new Parameter(column.getValue()));
    return "?";
  }

  @Override
  protected String visit(SimpleTable table) {
    return table.getTableName();
  }

  protected String visitSelectFromWhere(DslQuery query) {
    String conditionsSqlString = query.getConditions()
        .map(x -> " where " + visitFirstConditions(x))
        .orElse("");
    return visit(query.getSelectFrom())
        + conditionsSqlString;
  }

  protected String visitLimit(String allSqlWithoutLimit, Limit limit) {
    return allSqlWithoutLimit + visit(limit);
  }

  protected String visitFirstConditions(Conditions conditions) {
    return conditions.getConditionElements().stream()
        .map(this::visit)
        .collect(joining(" "));
  }
}