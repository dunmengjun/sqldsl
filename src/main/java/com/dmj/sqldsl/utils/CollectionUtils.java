package com.dmj.sqldsl.utils;

import java.util.Collection;
import java.util.function.Function;

public class CollectionUtils {

  public static <T, E> boolean hasDuplicateIn(Collection<T> collection, Function<T, E> function) {
    return collection.stream().map(function).distinct().count() < collection.size();
  }

  public static <T> boolean hasDuplicateIn(Collection<T> collection) {
    return hasDuplicateIn(collection, x -> x);
  }
}
