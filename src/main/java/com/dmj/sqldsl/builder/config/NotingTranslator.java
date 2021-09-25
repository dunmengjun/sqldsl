package com.dmj.sqldsl.builder.config;

public class NotingTranslator implements NameTranslator {

  @Override
  public String translate(String methodName) {
    return methodName;
  }
}
