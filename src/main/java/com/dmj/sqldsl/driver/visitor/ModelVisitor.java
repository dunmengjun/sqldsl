package com.dmj.sqldsl.driver.visitor;

import com.dmj.sqldsl.driver.exception.NotSupportedColumnException;
import com.dmj.sqldsl.driver.exception.NotSupportedConditionException;
import com.dmj.sqldsl.driver.exception.NotSupportedTableException;
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

public abstract class ModelVisitor {

  protected String visit(Column column) {
    if (column instanceof SimpleColumn) {
      return visit((SimpleColumn) column);
    } else if (column instanceof ValueColumn) {
      return visit((ValueColumn) column);
    }
    throw new NotSupportedColumnException(column);
  }

  protected String visit(Table table) {
    if (table instanceof SimpleTable) {
      return visit((SimpleTable) table);
    }
    throw new NotSupportedTableException(table);
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
    throw new NotSupportedConditionException(element);
  }

  protected abstract String visit(Conditions conditions);

  protected abstract String visit(Condition Condition);

  protected abstract String visit(And and);

  protected abstract String visit(Or or);

  protected abstract String visit(SimpleColumn column);

  protected abstract String visit(ValueColumn column);

  protected abstract String visit(SimpleTable table);
}
