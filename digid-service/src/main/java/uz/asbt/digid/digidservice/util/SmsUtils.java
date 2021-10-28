package uz.asbt.digid.digidservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SmsUtils {

  private static final DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy");

//  private static final DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy HH:mm:ss");

  public static Integer generateCodeForPhone() {
    return ThreadLocalRandom.current().nextInt(100000, 999999);
  }

  public static String getCurrentDate() {
    return LocalDateTime.now().format(customFormatter);
  }

}
