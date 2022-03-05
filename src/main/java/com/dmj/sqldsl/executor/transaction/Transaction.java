package com.dmj.sqldsl.executor.transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Transaction {

  private TransactionConnection connection;

  private final List<DefaultTransactionStatus> statuses;

  public static Transaction create(DefaultTransactionStatus status,
      TransactionConnection connection) {
    List<DefaultTransactionStatus> statuses = new ArrayList<>();
    statuses.add(status);
    return new Transaction(connection, statuses);
  }

  @SuppressWarnings({"SuspiciousMethodCalls"})
  public void remove(TransactionStatus status) {
    statuses.remove(status);
  }

  public void rollback() throws SQLException {
    statuses.forEach(status -> status.setCompleted(true));
    connection.rollback();
  }

  public void commit() throws SQLException {
    statuses.forEach(status -> status.setCompleted(true));
    connection.commit();
  }

  public void release() {
    try {
      connection.destroy();
    } catch (SQLException e) {
      throw new TransactionException(e);
    }
  }
}
