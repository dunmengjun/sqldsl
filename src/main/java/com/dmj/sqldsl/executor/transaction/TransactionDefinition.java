package com.dmj.sqldsl.executor.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TransactionDefinition {

  /**
   * 隔离级别
   */
  private Isolation isolation;

  /**
   * 传播属性
   */
  private Propagation propagation;

  public TransactionDefinition(Propagation propagation) {
    this.propagation = propagation;
    this.isolation = Isolation.READ_COMMITTED;
  }

  public TransactionDefinition(Isolation isolation) {
    this.isolation = isolation;
  }

  public static TransactionDefinition defaultDefinition() {
    return new TransactionDefinition(
        Isolation.READ_COMMITTED,
        Propagation.INTEGRATE_INTO);
  }
}
