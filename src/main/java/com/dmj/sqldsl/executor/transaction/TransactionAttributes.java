package com.dmj.sqldsl.executor.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TransactionAttributes {

  /**
   * 隔离级别
   */
  private Isolation isolation;

  /**
   * 传播属性
   */
  private Propagation propagation;

  public static TransactionAttributes defaultAttribute() {
    return new TransactionAttributes(
        Isolation.READ_COMMITTED,
        Propagation.INTEGRATE_INTO);
  }
}
