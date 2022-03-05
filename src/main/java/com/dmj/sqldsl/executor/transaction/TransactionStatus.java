package com.dmj.sqldsl.executor.transaction;

public interface TransactionStatus {

  boolean isNewTransaction();

  boolean isCompleted();
}
