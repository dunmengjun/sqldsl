package com.dmj.sqldsl.executor.transaction;

import static com.dmj.sqldsl.executor.transaction.TransactionManager.connectionThreadLocal;

import com.dmj.sqldsl.executor.ConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionConnectionManager implements ConnectionManager {

  private ConnectionManager connectionManager;

  public Connection getConnection() throws SQLException {
    Stack<Connection> connectionStack = connectionThreadLocal.get();
    if (connectionStack.isEmpty()) {
      return connectionManager.getConnection();
    }
    return connectionStack.peek();
  }
}
