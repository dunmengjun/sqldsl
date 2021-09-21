package com.dmj.sqldsl.utils;

import java.util.Date;

public class AssertUtils {

  public static boolean isBaseClass(Class<?> targetClass) {
    return targetClass.isPrimitive()
        || Byte.class.equals(targetClass)
        || Character.class.equals(targetClass)
        || Number.class.isAssignableFrom(targetClass)
        || Boolean.class.equals(targetClass)
        || Date.class.isAssignableFrom(targetClass);
  }
}
