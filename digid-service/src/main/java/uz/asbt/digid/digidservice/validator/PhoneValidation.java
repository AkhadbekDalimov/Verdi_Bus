package uz.asbt.digid.digidservice.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
@Slf4j
public class PhoneValidation {

  @Value("${app.phone.pattern}")
  private String phonePattern;

  public boolean isPhoneRegexpValid(final String phoneNumber) {
    if (phoneNumber == null || phoneNumber.isEmpty()) {
      return false;
    }
    final Pattern p = Pattern.compile(phonePattern);
    final Matcher m = p.matcher(phoneNumber);
    return m.matches();
  }
}
