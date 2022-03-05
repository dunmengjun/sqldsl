package com.dmj.sqldsl.executor.transaction;

import com.dmj.sqldsl.executor.ConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionConnectionManager implements ConnectionManager {

  private ConnectionManager connectionManager;

  public Connection getConnection() throws SQLException {
    Stack<Transaction> transactionStack = TransactionManager.stackThreadLocal.get();
    if (transactionStack.isEmpty()) {
      return connectionManager.getConnection();
    }
    return transactionStack.peek().getConnection();
  }
}
