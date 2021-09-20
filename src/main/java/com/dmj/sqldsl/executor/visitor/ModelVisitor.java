package com.dmj.sqldsl.executor.visitor;

import com.dmj.sqldsl.executor.exception.UnsupportedColumnException;
import com.dmj.sqldsl.executor.exception.UnsupportedConditionException;
import com.dmj.sqldsl.executor.exception.UnsupportedTableException;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.column.AliasColumn;
import com.dmj.sqldsl.model.column.Column;
import com.dmj.sqldsl.model.column.FunctionColumn;
import com.dmj.sqldsl.model.column.ListValueColumn;
import com.dmj.sqldsl.model.column.SimpleColumn;
import com.dmj.sqldsl.model.column.SubQueryValueColumn;
import com.dmj.sqldsl.model.column.ValueColumn;
import com.dmj.sqldsl.model.condition.And;
import com.dmj.sqldsl.model.condition.Condition;
import com.dmj.sqldsl.model.condition.ConditionElement;
import com.dmj.sqldsl.model.condition.Conditions;
import com.dmj.sqldsl.model.condition.Or;
import com.dmj.sqldsl.model.table.SimpleTable;
import com.dmj.sqldsl.model.table.SubQueryTable;
import com.dmj.sqldsl.model.table.Table;
import java.util.List;

public abstract class ModelVisitor {

  protected String visit(Column column) {
    if (column instanceof SimpleColumn) {
      return visit((SimpleColumn) column);
    } else if (column instanceof ValueColumn) {
      return visit((ValueColumn) column);
    } else if (column instanceof ListValueColumn) {
      return visit((ListValueColumn) column);
    } else if (column instanceof FunctionColumn) {
      return visit((FunctionColumn) column);
    } else if (column instanceof SubQueryValueColumn) {
      return visit((SubQueryValueColumn) column);
    } else if (column instanceof AliasColumn) {
      return visit((AliasColumn) column);
    }
    throw new UnsupportedColumnException(column);
  }

  protected String visit(Table table) {
    if (table instanceof SimpleTable) {
      return visit((SimpleTable) table);
    } else if (table instanceof SubQueryTable) {
      return visit((SubQueryTable) table);
    }
    throw new UnsupportedTableException(table);
  }

  protected String visit(ConditionElement element) {
    if (element instanceof Conditions) {
      return visit((Conditions) element);
    } else if (element instanceof Condition) {
      return visit((Condition) element);
    } else if (element instanceof Or) {
      return visit((Or) element);
    } else if (element instanceof And) {
      return visit((And) element);
    }
    throw new UnsupportedConditionException(element);
  }


  public abstract String visit(DslQuery query);

  protected abstract String visit(SubQueryValueColumn column);

  protected abstract String visit(FunctionColumn column);

  protected abstract String visit(Conditions conditions);

  protected abstract String visit(Condition condition);

  protected abstract String visit(And and);

  protected abstract String visit(Or or);

  protected abstract String visit(SimpleColumn column);

  protected abstract String visit(ValueColumn column);

  protected abstract String visit(ListValueColumn column);

  protected abstract String visit(AliasColumn column);

  protected abstract String visit(SimpleTable table);

  protected abstract String visit(SubQueryTable table);

  public abstract List<Parameter> getParams();
}
