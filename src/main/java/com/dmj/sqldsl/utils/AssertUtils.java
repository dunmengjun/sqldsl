package com.dmj.sqldsl.utils;

import com.dmj.sqldsl.builder.exception.UnmatchedTypeException;
import java.util.function.Supplier;

public class AssertUtils {

  public static <T extends RuntimeException> void assertEquals(
      Class<?> expectedType, Class<?> actualType, Supplier<T> supplier) {
    if (!wrapClass(expectedType).equals(wrapClass(actualType))) {
      throw supplier.get();
    }
  }

  public static void assertTypeMatched(Class<?> expectedType, Class<?> actualType) {
    assertEquals(expectedType, actualType,
        () -> new UnmatchedTypeException(expectedType, actualType));
  }

  private static Class<?> wrapClass(Class<?> targetClass) {
    if (!targetClass.isPrimitive()) {
      return targetClass;
    }
    if (int.class.equals(targetClass)) {
      return Integer.class;
    } else if (byte.class.equals(targetClass)) {
      return Byte.class;
    } else if (short.class.equals(targetClass)) {
      return Short.class;
    } else if (long.class.equals(targetClass)) {
      return Long.class;
    } else if (char.class.equals(targetClass)) {
      return Character.class;
    } else if (float.class.equals(targetClass)) {
      return Float.class;
    } else if (double.class.equals(targetClass)) {
      return Double.class;
    } else if (boolean.class.equals(targetClass)) {
      return Boolean.class;
    }
    return targetClass;
  }
}
