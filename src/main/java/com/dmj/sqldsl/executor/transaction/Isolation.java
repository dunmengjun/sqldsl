package com.dmj.sqldsl.executor.transaction;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;
import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

import lombok.Getter;

/**
 * 事务的隔离级别
 */
public enum Isolation {
  /**
   * 读已提交
   */
  READ_COMMITTED(TRANSACTION_READ_COMMITTED),
  /**
   * 读未提交
   */
  READ_UNCOMMITTED(TRANSACTION_READ_UNCOMMITTED),

  /**
   * 可重复读
   */
  REPEATABLE_READ(TRANSACTION_REPEATABLE_READ),

  /**
   * 串行
   */
  SERIALIZABLE(TRANSACTION_SERIALIZABLE);

  @Getter
  private final int level;

  Isolation(int level) {
    this.level = level;
  }
}
