package com.dmj.sqldsl.builder.config;

import static com.dmj.sqldsl.utils.StringUtils.toLowerCaseFirstOne;

public class PojoLikedStyleTranslator implements NameTranslator {

  @Override
  public String translate(String methodName) {
    return toLowerCaseFirstOne(methodName.substring(3));
  }
}
