package com.dmj.sqldsl.builder.config;

import com.dmj.sqldsl.utils.StringUtils;

public class FirstCharToLowerTranslator implements NameTranslator {

  @Override
  public String translate(String name) {
    return StringUtils.toLowerCaseFirstOne(name);
  }
}
