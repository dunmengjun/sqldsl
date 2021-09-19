package com.dmj.sqldsl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;

public class TestInnerClass {

  private class A {

    private class C {

    }
  }

  static class B {

  }

  @Test
  public void test()
      throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException {
    String className = "com.dmj.sqldsl.TestInnerClass$A$C";

    Object instance = newInstance(className);

    System.out.println(instance);
  }

  private Object newInstance(String className)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
    if (!className.contains("$")) {
      return Class.forName(className).newInstance();
    }
    Class<?> innerClass = Class.forName(className);
    Constructor<?> declaredConstructor = innerClass.getDeclaredConstructors()[0];
    declaredConstructor.setAccessible(true);
    int parameterCount = declaredConstructor.getParameterCount();
    Object instance;
    if (parameterCount == 0) {
      instance = declaredConstructor.newInstance();
    } else {
      int lastIndex = className.lastIndexOf("$");
      instance = declaredConstructor.newInstance(newInstance(className.substring(0, lastIndex)));
    }
    declaredConstructor.setAccessible(false);
    return instance;
  }
}
