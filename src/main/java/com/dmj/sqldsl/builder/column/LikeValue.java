package com.dmj.sqldsl.builder.column;

import static com.dmj.sqldsl.utils.StringUtils.assertNotEmpty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LikeValue {

  private String value;

  public static LikeValue startWith(String prefix) {
    assertNotEmpty(prefix, "The like prefix shouldn't be empty");
    return new LikeValue(prefix + "%");
  }

  public static LikeValue endWith(String end) {
    assertNotEmpty(end, "The like end shouldn't be empty");
    return new LikeValue("%" + end);
  }

  public static LikeValue contains(String value) {
    assertNotEmpty(value, "The like contains value shouldn't be empty");
    return new LikeValue("%" + value + "%");
  }

  public String build() {
    return value;
  }
}
