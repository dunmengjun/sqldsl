package com.dmj.sqldsl.executor.visitor;

import static java.util.stream.Collectors.joining;

import com.dmj.sqldsl.builder.Limit;
import com.dmj.sqldsl.executor.exception.NotSupportedJoinFlagException;
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
public class DslQueryVisitor extends ModelVisitor {

  private final List<Parameter> params;

  public DslQueryVisitor() {
    this.params = new ArrayList<>();
  }

  public String visit(DslQuery query) {
    String conditionsSqlString = query.getConditions()
        .map(x -> " where " + visitFirstConditions(x))
        .orElse("");
    String limitSqlString = query.getLimit().map(this::visit).orElse("");
    return visit(query.getSelectFrom())
        + conditionsSqlString
        + limitSqlString;
  }

  private String visit(Limit limit) {
    return String.format(" limit %s,%s", limit.getOffset(), limit.getSize());
  }

  private String visit(Join join) {
    return String.format("%s %s on %s", visit(join.getFlag()),
        visit(join.getTable()), visitFirstConditions(join.getConditions()));
  }

  private String visit(JoinFlag flag) {
    switch (flag) {
      case left:
        return "left join";
      case right:
        return "right join";
      case inner:
        return "inner join";
      default:
        throw new NotSupportedJoinFlagException(flag);
    }
  }

  private String visit(SelectFrom selectFrom) {
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
    return column.getAlias()
        .map(alias -> String.format("%s.%s as %s", column.getTableName(), column.getName(), alias))
        .orElse(String.format("%s.%s", column.getTableName(), column.getName()));
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

  private String visitFirstConditions(Conditions conditions) {
    return conditions.getConditionElements().stream()
        .map(this::visit)
        .collect(joining(" "));
  }
}
