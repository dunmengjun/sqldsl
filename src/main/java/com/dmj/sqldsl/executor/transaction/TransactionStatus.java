package com.dmj.sqldsl.executor.transaction;

import com.dmj.sqldsl.executor.exception.ExecutionException;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionStatus {

  private Connection connection;

  public void rollback() {
    try {
      connection.rollback();
    } catch (SQLException e) {
      throw new ExecutionException(e);
    }
  }

  public void commit() {
    try {
      connection.commit();
    } catch (SQLException e) {
      throw new ExecutionException(e);
    }
  }
}
