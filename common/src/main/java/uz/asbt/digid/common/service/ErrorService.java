package uz.asbt.digid.common.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ErrorService {

  private final Environment env;
  private final MessageSource messageSource;

  public Integer getErrorCode(final String code) {
    return Optional.ofNullable(env.getProperty(code, Integer.class)).orElse(9999);
  }

  public String getErrorMessage(final String message) {
    log.info("locale 444 {}", LocaleContextHolder.getLocale());
    return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
  }

}
