package uz.asbt.digid.crmservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.asbt.digid.common.exception.BusinessLogicException;
import uz.asbt.digid.common.models.IResponse;
import uz.asbt.digid.common.models.rest.Response;

import javax.persistence.RollbackException;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  @ExceptionHandler(value = Exception.class)
  protected ResponseEntity<IResponse> handleConflict(final Throwable error, final WebRequest request) {
    final Locale locale = request.getLocale();
    log.error(error.getMessage());

    final String message =
      Optional.of(error)
        .filter(e -> e instanceof BusinessLogicException)
        .map(BusinessLogicException.class::cast)
        .map(BusinessLogicException::getInfoMessage)
        .orElseGet(() -> Optional.of(error)
          .filter(e -> e instanceof RollbackException)
          .map(e -> messageSource.getMessage("message_9050", null, locale))
          .orElse(messageSource.getMessage("message_9999", null, locale)));

    final int code = Optional.of(error)
      .filter(e -> e instanceof BusinessLogicException)
      .map(BusinessLogicException.class::cast)
      .map(BusinessLogicException::getCode)
      .orElseGet(() -> Optional.of(error)
        .filter(e -> e instanceof RollbackException)
        .map(e -> 9050)
        .orElse(9999));

    log.info("ErrorMessage: {} - {}", code, message);
    return new ResponseEntity<>(new Response<>(code, message), HttpStatus.OK);
  }

}
