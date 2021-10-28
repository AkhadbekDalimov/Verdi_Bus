package uz.asbt.digid.crmservice.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Const {

  public static final String LOCAL_DATE_JSON_ISO_FORMAT = "dd-MM-yyyy";
  public static final String LOCAL_DATE_JSON_FORMAT = "dd.MM.yyyy";

  public static final String LOCAL_DATETIME_JSON_FORMAT = "dd-MM-yyyy HH:mm:ss";

  public static final DateTimeFormatter LOCAL_DATE_JSON_FORMATTER =
    DateTimeFormatter.ofPattern(Const.LOCAL_DATE_JSON_FORMAT);

}
