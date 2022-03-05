package com.dmj.sqldsl.executor.transaction;

/**
 * 事务的传播属性
 */
public enum Propagation {
  /**
   * 如果存在事务，就融入当前事务
   */
  INTEGRATE_INTO,
  /**
   * 和当前事务隔离，单独提交事务
   */
  ISOLATE
}
