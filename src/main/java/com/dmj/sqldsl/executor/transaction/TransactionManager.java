package com.dmj.sqldsl.executor.transaction;


import static com.dmj.sqldsl.executor.transaction.TransactionAttributes.defaultAttribute;

import com.dmj.sqldsl.executor.ConnectionManager;
import com.dmj.sqldsl.executor.exception.ExecutionException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionManager {

  private TransactionAttributes transactionAttributes;

  private ConnectionManager connectionManager;

  public TransactionManager(ConnectionManager connectionManager) {
    this.transactionAttributes = defaultAttribute();
    this.connectionManager = connectionManager;
  }

  protected static final ThreadLocal<Stack<Connection>> connectionThreadLocal;

  static {
    connectionThreadLocal = new InheritableThreadLocal<>();
    connectionThreadLocal.set(new Stack<>());
  }

  public void doTransaction(Consumer<TransactionStatus> consumer) {
    Connection connection = getConnection(this::getTransactionConnection);
    TransactionStatus transactionStatus = new TransactionStatus(connection);
    try {
      consumer.accept(transactionStatus);
    } finally {
      releaseConnection();
    }
  }

  private Connection getConnection(Supplier<Connection> getRealConn) {
    Stack<Connection> connectionStack = connectionThreadLocal.get();
    Propagation propagation = transactionAttributes.getPropagation();
    if (!connectionStack.isEmpty()
        && Propagation.INTEGRATE_INTO == propagation) {
      return connectionStack.peek();
    }
    Connection connection = getRealConn.get();
    connectionStack.push(connection);
    return connection;
  }

  private TransactionConnection getTransactionConnection() {
    try {
      Connection connection = connectionManager.getConnection();
      connection.setAutoCommit(false);
      return new TransactionConnection(connection);
    } catch (SQLException e) {
      throw new ExecutionException(e);
    }
  }

  @SuppressWarnings("MagicConstant")
  private void releaseConnection() {
    Connection connection = connectionThreadLocal.get().pop();
    try {
      connection.setAutoCommit(true);
      int level = transactionAttributes.getIsolation().getLevel();
      connection.setTransactionIsolation(level);
      connection.close();
    } catch (SQLException e) {
      throw new ExecutionException(e);
    }
  }
}
