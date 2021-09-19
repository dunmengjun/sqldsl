package com.dmj.sqldsl.utils;

import static com.dmj.sqldsl.utils.CollectionUtils.asModifiableList;

import com.dmj.sqldsl.utils.exception.ReflectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ReflectionUtils {

  @SuppressWarnings("unchecked")
  public static <T> T invokeMethod(String methodName, Object object, Object... args) {
    return accessMethod(getMethod(object.getClass(), methodName), m -> {
      try {
        return (T) m.invoke(object, args);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new ReflectionException(e);
      }
    });
  }

  public static Optional<Field> recursiveGetField(Class<?> targetClass, String fieldName) {
    Optional<Field> any = Arrays.stream(targetClass.getDeclaredFields())
        .filter(field -> field.getName().equals(fieldName))
        .findAny();
    if (any.isPresent()) {
      return any;
    }
    Class<?> superclass = targetClass.getSuperclass();
    if (superclass != null) {
      return recursiveGetField(superclass, fieldName);
    } else {
      return Optional.empty();
    }
  }

  public static Stream<Field> recursiveGetFields(Class<?> targetClass,
      Class<? extends Annotation> annotationClass) {
    Stream<Field> fieldStream = asModifiableList(targetClass.getDeclaredFields()).stream()
        .filter(field -> field.isAnnotationPresent(annotationClass));
    Class<?> superclass = targetClass.getSuperclass();
    while (superclass != null) {
      Stream<Field> superClassFieldStream = Arrays.stream(superclass.getDeclaredFields())
          .filter(field -> field.isAnnotationPresent(annotationClass));
      fieldStream = Stream.concat(fieldStream, superClassFieldStream);
      superclass = superclass.getSuperclass();
    }
    return fieldStream;
  }

  public static Method getMethod(Class<?> targetClass, String methodName) {
    try {
      return targetClass.getDeclaredMethod(methodName);
    } catch (NoSuchMethodException e) {
      throw new ReflectionException(e);
    }
  }

  public static Class<?> forName(String classPath) {
    try {
      return Class.forName(classPath);
    } catch (ClassNotFoundException e) {
      throw new ReflectionException(e);
    }
  }

  public static <T> T newInstance(Class<T> targetClass) {
    return newInstance(targetClass.getName());
  }

  @SuppressWarnings("unchecked")
  public static <T> T newInstance(String className) {
    try {
      if (!className.contains("$")) {
        return (T) Class.forName(className).newInstance();
      }
      Constructor<?>[] constructors = Class.forName(className).getDeclaredConstructors();
      int lastIndex = className.lastIndexOf("$");
      String outer = className.substring(0, lastIndex);
      for (Constructor<?> constructor : constructors) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if (parameterTypes.length == 0) {
          constructor.setAccessible(true);
          Object instance = constructor.newInstance();
          constructor.setAccessible(false);
          return (T) instance;
        } else if (parameterTypes.length == 1 && parameterTypes[0].getName().equals(outer)) {
          constructor.setAccessible(true);
          Object instance = constructor.newInstance(newInstance(outer));
          constructor.setAccessible(false);
          return (T) instance;
        }
      }
      throw new ReflectionException("No zero parameter constructor in class " + className);
    } catch (ClassNotFoundException | InvocationTargetException
        | InstantiationException | IllegalAccessException e) {
      throw new ReflectionException(e);
    }
  }

  public static boolean recursiveSetValue(String fieldName, Object instance, Object value) {
    return recursiveGetField(instance.getClass(), fieldName)
        .map(field ->
            accessField(field, f -> {
              try {
                f.set(instance, value);
                return true;
              } catch (IllegalAccessException e) {
                throw new ReflectionException(e);
              }
            }))
        .orElse(false);
  }

  private static <T> T accessField(Field field, Function<Field, T> function) {
    if (!field.isAccessible()) {
      field.setAccessible(true);
      T apply = function.apply(field);
      field.setAccessible(false);
      return apply;
    } else {
      return function.apply(field);
    }
  }

  private static <T> T accessMethod(Method method, Function<Method, T> function) {
    if (!method.isAccessible()) {
      method.setAccessible(true);
      T apply = function.apply(method);
      method.setAccessible(false);
      return apply;
    } else {
      return function.apply(method);
    }
  }
}
