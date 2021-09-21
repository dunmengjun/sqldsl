package com.dmj.sqldsl.executor.visitor;

import static java.util.stream.Collectors.joining;

import com.dmj.sqldsl.executor.exception.UnsupportedConditionMethodException;
import com.dmj.sqldsl.executor.exception.UnsupportedFunctionException;
import com.dmj.sqldsl.executor.exception.UnsupportedJoinFlagException;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.GroupBy;
import com.dmj.sqldsl.model.Join;
import com.dmj.sqldsl.model.JoinFlag;
import com.dmj.sqldsl.model.Limit;
import com.dmj.sqldsl.model.SelectFrom;
import com.dmj.sqldsl.model.column.AliasColumn;
import com.dmj.sqldsl.model.column.Function;
import com.dmj.sqldsl.model.column.FunctionColumn;
import com.dmj.sqldsl.model.column.ListValueColumn;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.model.column.SubQueryValueColumn;
import com.dmj.sqldsl.model.column.ValueColumn;
import com.dmj.sqldsl.model.condition.And;
import com.dmj.sqldsl.model.condition.Condition;
import com.dmj.sqldsl.model.condition.ConditionElement;
import com.dmj.sqldsl.model.condition.ConditionMethod;
import com.dmj.sqldsl.model.condition.Conditions;
import com.dmj.sqldsl.model.condition.Or;
import com.dmj.sqldsl.model.table.SimpleTable;
import com.dmj.sqldsl.model.table.SubQueryTable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

  @Override
  protected String visit(SubQueryValueColumn column) {
    return String.format("(%s)", visit(column.getQuery()));
  }

  @Override
  protected String visit(FunctionColumn column) {
    String params = column.getParams().stream().map(this::visit).collect(joining(","));
    return String.format("%s(%s)", visit(column.getFunction()), params);
  }

  private String visit(Function function) {
    switch (function) {
      case count:
        return "count";
      case sum:
        return "sum";
      case avg:
        return "avg";
      case max:
        return "max";
      case min:
        return "min";
      case distinct:
        return "distinct";
      default:
        throw new UnsupportedFunctionException(function);
    }
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
    String tablesSqlString = visit(selectFrom.getTable());
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
    return String.format("%s %s %s",
        visit(condition.getLeft()),
        visit(condition.getMethod()),
        visit(condition.getRight()));
  }

  private String visit(ConditionMethod method) {
    switch (method) {
      case eq:
        return "=";
      case ge:
        return ">=";
      case gt:
        return ">";
      case in:
        return "in";
      case le:
        return "<=";
      case lt:
        return "<";
      case ne:
        return "!=";
      case like:
        return "like";
      case notLike:
        return "not like";
      case notIn:
        return "not in";
      default:
        throw new UnsupportedConditionMethodException(method);
    }
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
    return column.getTableName()
        .map(tableName -> String.format("%s.%s", tableName, column.getName()))
        .orElse(column.getName());
  }

  @Override
  protected String visit(ValueColumn column) {
    params.add(new Parameter(column.getValue()));
    return "?";
  }

  @Override
  protected String visit(ListValueColumn column) {
    return column.getList().stream().map(x -> {
      params.add(new Parameter(x));
      return "?";
    }).collect(Collectors.joining(",", "(", ")"));
  }

  @Override
  protected String visit(AliasColumn column) {
    return String.format("%s as %s", visit(column.getColumn()), column.getAlias());
  }

  @Override
  protected String visit(SimpleTable table) {
    return table.getAlias()
        .map(alias -> String.format("%s as %s", table.getTableName(), alias))
        .orElse(table.getTableName());
  }

  @Override
  protected String visit(SubQueryTable table) {
    return String.format("(%s) %s", visit(table.getQuery()), table.getAlias());
  }

  private String visit(GroupBy groupBy) {
    String columnsSql = groupBy.getColumns().stream()
        .map(this::visit)
        .collect(joining(","));
    String havingSql = groupBy.getConditions()
        .map(conditions -> String.format(" having %s", visitFirstConditions(conditions)))
        .orElse("");
    return String.format(" group by %s%s", columnsSql, havingSql);
  }

  protected String visitSelectFromWhere(DslQuery query) {
    String selectFrom = visit(query.getSelectFrom());
    String conditions = query.getConditions()
        .map(x -> " where " + visitFirstConditions(x))
        .orElse("");
    String groupBy = query.getGroupBy().map(this::visit).orElse("");
    return selectFrom + conditions + groupBy;
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
