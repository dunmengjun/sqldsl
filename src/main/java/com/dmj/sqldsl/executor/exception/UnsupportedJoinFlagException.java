package com.dmj.sqldsl.executor.exception;

import com.dmj.sqldsl.model.JoinFlag;
import lombok.Getter;

@Getter
public class UnsupportedJoinFlagException extends RuntimeException {

  private final JoinFlag flag;

  public UnsupportedJoinFlagException(JoinFlag flag) {
    super("Unsupported join flag: " + flag);
    this.flag = flag;
  }
}
