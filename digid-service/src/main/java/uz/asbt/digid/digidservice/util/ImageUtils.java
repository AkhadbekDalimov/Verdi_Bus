package uz.asbt.digid.digidservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageUtils {

  public static final String splitBase64File(final String base64File) {
    final String[] arr = base64File.split(",");
    if (arr.length == 2) {
      return arr[1];
    } else {
      return arr[0];
    }
  }
}
