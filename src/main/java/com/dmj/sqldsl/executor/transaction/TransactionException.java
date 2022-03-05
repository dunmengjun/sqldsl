package com.dmj.sqldsl.executor.transaction;

public class TransactionException extends RuntimeException {

  public TransactionException(String message) {
    super(message);
  }

  public TransactionException(Throwable cause) {
    super(cause);
  }
}
