package com.dmj.sqldsl.driver.exception;

import com.dmj.sqldsl.model.JoinFlag;
import lombok.Getter;

@Getter
public class NotSupportedJoinFlagException extends RuntimeException {

  private final JoinFlag flag;

  public NotSupportedJoinFlagException(JoinFlag flag) {
    super("Not supported join flag: " + flag);
    this.flag = flag;
  }
}
