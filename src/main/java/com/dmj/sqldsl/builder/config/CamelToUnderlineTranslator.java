package com.dmj.sqldsl.builder.config;

import com.dmj.sqldsl.utils.StringUtils;

public class CamelToUnderlineTranslator implements NameTranslator {

  @Override
  public String translate(String camelCase) {
    return StringUtils.toUnderlineCase(camelCase);
  }
}
