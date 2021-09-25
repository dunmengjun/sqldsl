package com.dmj.sqldsl.utils;

public class StringUtils {

  public static boolean isBlank(String value) {
    return value == null || value.trim().equals("");
  }

  public static String toLowerCaseFirstOne(String s) {
    if (Character.isLowerCase(s.charAt(0))) {
      return s;
    } else {
      return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }
  }

  public static void assertNotEmpty(String value, String message) {
    if (isBlank(value)) {
      throw new IllegalArgumentException(message);
    }
  }

  public static String toUnderlineCase(String camelCase) {
    if (camelCase == null) {
      return null;
    }
    char[] charArray = camelCase.toCharArray();
    StringBuilder builder = new StringBuilder();
    for (int i = 0, l = charArray.length; i < l; i++) {
      if (charArray[i] >= 65 && charArray[i] <= 90) {
        builder.append("_").append(charArray[i] += 32);
      } else {
        builder.append(charArray[i]);
      }
    }
    return builder.toString();
  }
}
