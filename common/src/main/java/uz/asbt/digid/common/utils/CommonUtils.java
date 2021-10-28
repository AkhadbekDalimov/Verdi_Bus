package uz.asbt.digid.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

  public static final String REALM_NAME = "Liveness";

  public static <T> List<T> emptyListIfNull(final List<T> list) {
    return list == null ? Collections.emptyList() : list;
  }

  public static String getMd5(final String password)
  {
    return Optional.ofNullable(password)
      .map(p -> {
        MessageDigest md = null;
        try {
          md = MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException e) {
          throw new RuntimeException();
        }
        final byte[] messageDigest = md.digest(password.getBytes());
        final BigInteger no = new BigInteger(1, messageDigest);
        final StringBuilder hashtext = new StringBuilder(no.toString(16));
        while (hashtext.length() < 32) {
          hashtext.insert(0, "0");
        }
        return hashtext.toString();
      }).orElseThrow(RuntimeException::new);
  }

  public static <T> boolean isEmptyCollection(final Collection<T> collection) {
    return collection == null || collection.isEmpty();
  }
  public static <T> boolean isNonEmptyCollection(final Collection<T> collection) {
    return !isEmptyCollection(collection);
  }

  public static <T> Collection<T> emptyCollectionIfNull(final Collection<T> collection) {
    return collection == null ? Collections.emptyList() : collection;
  }

  public static <T> Collection<T> newCollection(final Collection<T> collection) {
    return Optional.ofNullable(collection).map(LinkedList::new).orElseGet(LinkedList::new);
  }

  public static Collection<Long> getLongCollectionFromStrings(final Collection<String> value) {
    return emptyCollectionIfNull(value)
             .stream()
             .map(obj -> Arrays.asList(StringUtils.split(obj, ",")))
             .flatMap(Collection::stream)
             .map(Long::valueOf)
             .distinct()
             .collect(Collectors.toList());
  }

  public static String getTrimAndEscapeString(final String s) {
    return StringUtils.stripToNull(StringEscapeUtils.escapeSql(s));
  }

  public static void runIfTrue(final boolean check, final Runnable runnable) {
    if (check) {
      runnable.run();
    }
  }

  public static void runIfFalse(final boolean check, final Runnable runnable) {
    runIfTrue(!check, runnable);
  }

  public static <T extends RuntimeException> void throwIfTrue(final boolean check, final Supplier<T> runtimeException) {
    runIfTrue(check, () -> {
      throw runtimeException.get();
    });
  }

  public static <V, T extends RuntimeException> void throwIfNull(final V v, final Supplier<T> runtimeException) {
    runIfTrue(Objects.isNull(v), () -> {
      throw runtimeException.get();
    });
  }

  public static <T> void throwIfEmpty(final Collection<T> collection, final RuntimeException ex) {
    if (collection == null || collection.isEmpty())
      throw ex;
  }

  public static <T extends RuntimeException> void throwIfFalse(final boolean check, final Supplier<T> runtimeException) {
    throwIfTrue(!check, runtimeException);
  }

  public static <T> void runIfNullOrElse(final T t, final Runnable trueBranch, final Runnable falseBranch) {
    ifElse(t == null, trueBranch, falseBranch);
  }

  public static <T> void runIfNull(final T t, final Runnable runnable) {
    runIfTrue(t == null, runnable);
  }

  public static <T> void runIfNotNull(final T t, final Runnable runnable) {
    runIfTrue(t != null, runnable);
  }

  public static <T> void runIfNotNullOrElse(final T t, final Runnable trueBranch, final Runnable falseBranch) {
    runIfNotNullOrElse(t, falseBranch, trueBranch);
  }

  public static void ifElse(final boolean condition, final Runnable trueBranch, final Runnable falseBranch) {
    if (condition) {
      trueBranch.run();
    } else {
      falseBranch.run();
    }
  }

  public static <T> boolean in(final T candidate, final List<T> probes) {
    return probes != null && probes.stream().anyMatch(probe -> probe == candidate);
  }

}
