package uz.asbt.digid.crmservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;

import static uz.asbt.digid.common.constants.Const.LOCAL_DATE_JSON_FORMATTER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

  public static boolean checkStatusChangedDate(final String expiryDate) {
    if (expiryDate == null) {
      return false;
    }
    final LocalDate date = LocalDate.parse(expiryDate, LOCAL_DATE_JSON_FORMATTER);
    final LocalDate today = LocalDate.now(ZoneId.systemDefault());
    return today.isBefore(date);
  }
}
