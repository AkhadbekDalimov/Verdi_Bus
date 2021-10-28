package uz.asbt.digid.common.utils;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

  /**
   * Преобразовать iterable в Stream
   */
  public static <T> Stream<T> iterableToStream(final Iterable<T> iterable, final boolean parallel) {
    return Optional.ofNullable(iterable)
             .map(i -> StreamSupport.stream(i.spliterator(), parallel))
             .orElse(null);
  }

  /**
   * Преобразовать iterable в Stream (в одном потоке)
   */
  public static <T> Stream<T> iterableToStream(final Iterable<T> iterable) {
    return iterableToStream(iterable, false);
  }

  /**
   * Преобразовать iterable в Stream (многопоточная версия)
   */
  public static <T> Stream<T> iterableToStreamParallel(final Iterable<T> iterable) {
    return iterableToStream(iterable, true);
  }


  /**
   * Преобразовать iterable в Stream
   */
  public static <T> Stream<T> of(final Iterable<T> iterable, final boolean parallel) {
    return Optional.ofNullable(iterable)
      .map(i -> StreamSupport.stream(i.spliterator(), parallel))
      .orElseGet(Stream::empty);
  }

  /**
   * Преобразовать iterable в Stream (в одном потоке)
   */
  public static <T> Stream<T> of(final Iterable<T> iterable) {
    return of(iterable, false);
  }

}
