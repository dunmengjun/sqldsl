package com.dmj.sqldsl.executor.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class DefaultTransactionStatus implements TransactionStatus {

  private boolean newTransaction;

  @Setter
  private boolean completed;

  public DefaultTransactionStatus(boolean newTransaction) {
    this.newTransaction = newTransaction;
    this.completed = false;
  }
}
