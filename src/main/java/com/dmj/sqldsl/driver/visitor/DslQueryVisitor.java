package com.dmj.sqldsl.driver.visitor;

import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.Select;
import com.dmj.sqldsl.model.SimpleTable;
import com.dmj.sqldsl.model.Table;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.model.condition.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class DslQueryVisitor extends ModelVisitor {

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

    private String visitWhere(Conditions conditions) {
        String conditionsSql = conditions.getConditionElements().stream()
                .map(this::visit)
                .collect(joining(" "));
        return Optional.of(conditionsSql)
                .filter(x -> !x.isEmpty())
                .map(x -> "where " + x)
                .orElse("");
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
        return String.format("%s = %s", visit(column), visit(value));
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

    private String visit(Object value) {
        if (value instanceof String) {
            return String.format("'%s'", value);
        }
        return value.toString();
    }
}
