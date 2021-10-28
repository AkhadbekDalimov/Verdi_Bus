package uz.asbt.digid.common.utils;

import java.util.List;
import java.util.stream.Collectors;

public class ListUtils {

  public static <T> List<T> iterableToList(final Iterable<T> iterable, final boolean parallel) {
    return StreamUtils.iterableToStream(iterable, parallel)
             .collect(Collectors.toList());
  }

  public static <T> List<T> iterableToList(final Iterable<T> iterable) {
    return iterableToList(iterable, false);
  }

  public static <T> List<T> iterableToListParallel(final Iterable<T> iterable) {
    return iterableToList(iterable, true);
  }

}
