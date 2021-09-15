package com.dmj.sqldsl.utils;

import java.util.function.Supplier;

public class AssertUtils {

  public static <T extends RuntimeException> void assertEquals(
      Class<?> expectedType, Class<?> actualType, Supplier<T> supplier) {
    if (!expectedType.equals(actualType)) {
      throw supplier.get();
    }
  }
}
