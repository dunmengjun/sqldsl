package com.dmj.sqldsl.driver.visitor;

import static java.util.stream.Collectors.joining;

import com.dmj.sqldsl.driver.exception.NotSupportedJoinFlagException;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Join;
import com.dmj.sqldsl.model.JoinFlag;
import com.dmj.sqldsl.model.SelectFrom;
import com.dmj.sqldsl.model.SimpleTable;
import com.dmj.sqldsl.model.Table;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.model.column.ValueColumn;
import com.dmj.sqldsl.model.condition.And;
import com.dmj.sqldsl.model.condition.Condition;
import com.dmj.sqldsl.model.condition.ConditionElement;
import com.dmj.sqldsl.model.condition.Conditions;
import com.dmj.sqldsl.model.condition.Or;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

@Getter
public class DslQueryVisitor extends ModelVisitor {

  private final List<Parameter> params;

  public DslQueryVisitor() {
    this.params = new ArrayList<>();
  }

  public String visit(DslQuery query) {
    String selectSqlString = visit(query.getSelectFrom());
    String conditionsSqlString = visitWhere(query.getConditions());
    return String.format("%s %s", selectSqlString, conditionsSqlString);
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
    List<Column> columns = selectFrom.getColumns();
    String columnsSqlString = columns.stream()
        .map(this::visit)
        .collect(joining(", "));
    List<Table> tables = selectFrom.getTables();
    String tablesSqlString = tables.stream()
        .map(this::visit)
        .collect(joining(","));
    String joinSqlString = selectFrom.getJoins().stream()
        .map(this::visit)
        .collect(joining(" "));
    return String.format("select %s from %s %s", columnsSqlString, tablesSqlString, joinSqlString);
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
    return String.format("%s.%s", column.getTableName(), column.getName());
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

  private String visitWhere(Conditions conditions) {
    return Optional.of(visitFirstConditions(conditions))
        .filter(x -> !x.isEmpty())
        .map(x -> "where " + x)
        .orElse("");
  }

  private String visitFirstConditions(Conditions conditions) {
    return conditions.getConditionElements().stream()
        .map(this::visit)
        .collect(joining(" "));
  }
}
