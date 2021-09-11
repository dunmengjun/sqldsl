package com.dmj.sqldsl.driver.visitor;

import static java.util.stream.Collectors.joining;

import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Select;
import com.dmj.sqldsl.model.SimpleTable;
import com.dmj.sqldsl.model.Table;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.model.condition.And;
import com.dmj.sqldsl.model.condition.ColumnValueCondition;
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
    String selectSqlString = visit(query.getSelect());
    String conditionsSqlString = visitWhere(query.getConditions());
    return String.format("%s %s", selectSqlString, conditionsSqlString);
  }

  private String visit(Select select) {
    List<Column> columns = select.getColumns();
    String columnsSqlString = columns.stream()
        .map(this::visit)
        .collect(joining(", "));
    List<Table> tables = select.getTables();
    String tablesSqlString = tables.stream()
        .map(this::visit)
        .collect(joining(","));
    return String.format("select %s from %s", columnsSqlString, tablesSqlString);
  }

  @Override
  protected String visit(Conditions conditions) {
    List<ConditionElement> conditionElements = conditions.getConditionElements();
    return conditionElements.stream()
        .map(this::visit)
        .collect(joining(" ", "(", ")"));
  }

  @Override
  protected String visit(ColumnValueCondition valueCondition) {
    Column column = valueCondition.getColumn();
    Object value = valueCondition.getValue();
    params.add(new Parameter(value.getClass(), value));
    return String.format("%s = ?", visit(column));
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
  protected String visit(SimpleTable table) {
    return table.getTableName();
  }

  private String visitWhere(Conditions conditions) {
    String conditionsSql = conditions.getConditionElements().stream()
        .map(this::visit)
        .collect(joining(" "));
    return Optional.of(conditionsSql)
        .filter(x -> !x.isEmpty())
        .map(x -> "where " + x)
        .orElse("");
  }
}
