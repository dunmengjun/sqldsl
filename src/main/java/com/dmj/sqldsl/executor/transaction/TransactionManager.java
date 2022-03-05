package com.dmj.sqldsl.executor.transaction;


import com.dmj.sqldsl.executor.ConnectionManager;
import com.dmj.sqldsl.executor.exception.ExecutionException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionManager {

  private ConnectionManager connectionManager;

  protected static final ThreadLocal<Map<Object, Transaction>>
      transactionMapThreadLocal = ThreadLocal.withInitial(HashMap::new);
  protected static final ThreadLocal<Stack<Transaction>>
      stackThreadLocal = ThreadLocal.withInitial(Stack::new);

  public TransactionStatus getTransaction(TransactionDefinition definition) {
    Map<Object, Transaction> transactionMap = transactionMapThreadLocal.get();
    Stack<Transaction> stack = stackThreadLocal.get();
    if (transactionMap.isEmpty()) {
      TransactionConnection connection = getTransactionConnection(definition);
      DefaultTransactionStatus status = new DefaultTransactionStatus(true);
      Transaction transaction = Transaction.create(status, connection);
      transactionMap.put(status, transaction);
      stack.push(transaction);
      return status;
    }
    Propagation propagation = definition.getPropagation();
    if (Propagation.ISOLATE == propagation) {
      TransactionConnection connection = getTransactionConnection(definition);
      DefaultTransactionStatus status = new DefaultTransactionStatus(true);
      Transaction transaction = Transaction.create(status, connection);
      transactionMap.put(status, transaction);
      stack.push(transaction);
      return status;
    }
    Transaction transaction = stack.peek();
    DefaultTransactionStatus intoStatus = new DefaultTransactionStatus(false);
    transactionMap.put(intoStatus, transaction);
    transaction.getStatuses().add(intoStatus);
    return intoStatus;
  }

  public void commit(TransactionStatus status) {
    if (status.isCompleted()) {
      throw new TransactionException("Can not commit finished transaction!");
    }
    Map<Object, Transaction> transactionMap = transactionMapThreadLocal.get();
    if (transactionMap.isEmpty()) {
      throw new TransactionException("No transaction content!");
    }
    Transaction transaction = transactionMap.get(status);
    if (transaction == null) {
      throw new TransactionException("No transaction found!");
    }
    if (!status.isNewTransaction()) {
      transaction.remove(status);
      transactionMap.remove(status);
      return;
    }
    try {
      transaction.commit();
    } catch (SQLException e) {
      throw new TransactionException(e);
    } finally {
      transaction.getStatuses().forEach(transactionMap::remove);
      Stack<Transaction> stack = stackThreadLocal.get();
      stack.remove(transaction);
      transaction.release();
    }
  }

  public void rollback(TransactionStatus status) {
    if (status.isCompleted()) {
      throw new TransactionException("Can not rollback finished transaction!");
    }
    Map<Object, Transaction> transactionMap = transactionMapThreadLocal.get();
    if (transactionMap.isEmpty()) {
      throw new TransactionException("No transaction content!");
    }
    Transaction transaction = transactionMap.get(status);
    if (transaction == null) {
      throw new TransactionException("No transaction found!");
    }
    try {
      transaction.rollback();
    } catch (SQLException e) {
      throw new TransactionException(e);
    } finally {
      transaction.getStatuses().forEach(transactionMap::remove);
      Stack<Transaction> stack = stackThreadLocal.get();
      stack.remove(transaction);
      transaction.release();
    }
  }


  @SuppressWarnings("MagicConstant")
  private TransactionConnection getTransactionConnection(TransactionDefinition definition) {
    try {
      Connection connection = connectionManager.getConnection();
      int level = definition.getIsolation().getLevel();
      connection.setTransactionIsolation(level);
      connection.setAutoCommit(false);
      return new TransactionConnection(connection);
    } catch (SQLException e) {
      throw new ExecutionException(e);
    }
  }
}
