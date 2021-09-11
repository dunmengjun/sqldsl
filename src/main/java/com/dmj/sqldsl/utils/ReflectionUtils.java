package com.dmj.sqldsl.utils;

import com.dmj.sqldsl.utils.exception.ReflectionException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class ReflectionUtils {

    public static <T> T getValue(String fieldName, Object object) {
        return accessField(getField(object.getClass(), fieldName), field -> {
            try {
                return (T) field.get(object);
            } catch (IllegalAccessException e) {
                throw new ReflectionException(e);
            }
        });
    }

    public static <T> T invokeMethod(String methodName, Object object, Object... args) {
        return accessMethod(getMethod(object.getClass(), methodName), m -> {
            try {
                return (T) m.invoke(object, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ReflectionException(e);
            }
        });
    }

    public static Field getField(Class<?> targetClass, String fieldName) {
        try {
            return targetClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new ReflectionException(e);
        }
    }

    public static Method getMethod(Class<?> targetClass, String methodName) {
        try {
            return targetClass.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new ReflectionException(e);
        }
    }

    public static <T> T newInstance(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }

    public static void setValue(String fieldName, Object instance, Object value) {
        accessField(getField(instance.getClass(), fieldName), f -> {
            try {
                f.set(instance, value);
            } catch (IllegalAccessException e) {
                throw new ReflectionException(e);
            }
            return null;
        });
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
