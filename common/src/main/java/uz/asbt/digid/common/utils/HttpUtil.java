package uz.asbt.digid.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;

import javax.servlet.http.HttpServletRequest;
import javax.swing.table.TableRowSorter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class HttpUtil {

  public static HttpHeaders headers(HttpServletRequest request) {
    HttpHeaders httpHeaders = new HttpHeaders();
    enumerationAsStream(request.getHeaderNames()).forEach(i -> httpHeaders.add(i, request.getHeader(i)));
    return httpHeaders;
  }

  public static <T> Stream<T> enumerationAsStream(Enumeration<T> e) {
    return StreamSupport.stream(
      Spliterators.spliteratorUnknownSize(
        new Iterator<T>() {
          public T next() {
            return e.nextElement();
          }

          public boolean hasNext() {
            return e.hasMoreElements();
          }
        },
        Spliterator.ORDERED), false);
  }

  public static boolean checkSign(String signString, String pubKey, ModelPersonAnswere personAnswere) throws Exception {
    return signString.equals(generateSignMobile(pubKey, personAnswere)) || signString.equals(generateSign(pubKey, personAnswere))
      || signString.equals(generateSignV2(pubKey, personAnswere)) || signString.equals(generateSignV4(pubKey, personAnswere));
  }

  private static String generateSignMobile(String pubKey, ModelPersonAnswere personAnswere) throws Exception {
    try {
      StringBuilder builder = new StringBuilder();
      builder.append(personAnswere.getRequestGuid());
      builder.append(personAnswere.getModelServiceInfo().getServiceInfo().getScannerSerial());
      if (personAnswere.getModelMobileData() == null) {
        return null;
      }
      builder.append(personAnswere.getModelMobileData().getMobileDeviceId());
      builder.append(pubKey);
      return getHashString(builder.toString());
    } catch (final Exception ex) {
      log.error("error mobile sing {}", personAnswere);
      return "";
    }
  }

  public static String generateSignV2(String pubKey, ModelPersonAnswere personAnswere) throws Exception {
    try {
      if (personAnswere.getModelPersonPassport() != null && personAnswere.getModelPersonPassport().getPersonPassport() != null) {
        StringBuilder builder = new StringBuilder();
        builder.append(personAnswere.getModelPersonPassport().getPersonPassport().getDocumentNumber());
        builder.append(personAnswere.getModelPersonPassport().getPersonPassport().getBirthDate());
        builder.append(personAnswere.getModelPersonPassport().getPersonPassport().getExpiryDate());
        builder.append(pubKey);
        return getHashString(builder.toString());
      } else {
        return null;
      }
    } catch (final Exception ex) {
      log.error("error sing V2 {}", personAnswere);
      return "";
    }
  }

  public static String generateSignV4(String pubKey, ModelPersonAnswere personAnswere) throws Exception {
    try {
      if (personAnswere.getModelPersonPassport() != null && personAnswere.getModelPersonPassport().getPersonPassport() != null
        && personAnswere.getModelServiceInfo() != null && personAnswere.getModelServiceInfo().getServiceInfo() != null) {
        StringBuilder builder = new StringBuilder();
        builder.append(personAnswere.getModelServiceInfo().getServiceInfo().getScannerSerial());
        builder.append(personAnswere.getModelServiceInfo().getServiceInfo().getClientMAC());
        builder.append(personAnswere.getRequestGuid());
        builder.append(pubKey);
        return getHashString(builder.toString());
      } else {
        return null;
      }
    } catch (final Exception ex) {
      log.error("error sing V4 {}", personAnswere);
      return "";
    }
  }

  public static String generateSign(String pubKey, ModelPersonAnswere personAnswere) throws Exception {
    try {
      StringBuilder builder = new StringBuilder();
      if (personAnswere.getModelPersonPassport() != null && personAnswere.getModelPersonPassport().getPersonPassport() != null) {
        builder.append(personAnswere.getModelPersonPassport().getPersonPassport().getDocumentNumber());
        builder.append(personAnswere.getModelPersonPassport().getPersonPassport().getName());
        builder.append(personAnswere.getModelPersonPassport().getPersonPassport().getSurname());
        builder.append(personAnswere.getModelPersonPassport().getPersonPassport().getDocumentType());
        builder.append(pubKey);
        return getHashString(builder.toString());
      } else {
        return null;
      }
    } catch (final Exception ex) {
      log.error("error sing V1 {}", personAnswere);
      return "";
    }
  }

  public static String getHashString(String str) throws Exception {
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] hashInBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
    StringBuilder sb = new StringBuilder();
    for (byte b : hashInBytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

}
