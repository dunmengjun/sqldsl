package com.dmj.sqldsl.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionUtils {

  public static <T, E> boolean hasDuplicateIn(Collection<T> collection, Function<T, E> function) {
    return collection.stream().map(function).distinct().count() < collection.size();
  }

  public static <T> boolean hasDuplicateIn(Collection<T> collection) {
    return hasDuplicateIn(collection, x -> x);
  }

  @SafeVarargs
  public static <T> List<T> asModifiableList(T... objects) {
    return new ArrayList<>(Arrays.asList(objects));
  }
}
