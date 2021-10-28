package uz.asbt.digid.digidservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.asbt.digid.common.exception.BusinessLogicException;
import uz.asbt.digid.common.models.entity.client.Answere;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.Response;

import java.util.Locale;
import java.util.Optional;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  @ExceptionHandler(value = {
    Exception.class
  })
  protected ResponseEntity<Response<ModelPersonAnswere>> handleConflict(final Exception ex,
                                                                        final WebRequest request) {
    log.error(ex.getMessage());

    final Locale locale =
      Optional.ofNullable((Locale) request.getAttribute("locale", RequestAttributes.SCOPE_REQUEST))
        .orElse(Locale.forLanguageTag("ru"));
    final ModelPersonAnswere person = (ModelPersonAnswere) request
      .getAttribute("person", RequestAttributes.SCOPE_REQUEST);

    final int code = Optional.of(ex)
      .filter(e -> e instanceof BusinessLogicException)
      .map(BusinessLogicException.class::cast)
      .map(BusinessLogicException::getCode)
      .orElse(9999);

    final String message = Optional.of(ex)
      .filter(e -> e instanceof BusinessLogicException)
      .map(BusinessLogicException.class::cast)
      .map(BusinessLogicException::getCode)
      .map(c -> messageSource.getMessage("message_" + c, null, locale))
      .orElse(messageSource.getMessage("message_9999", null, locale));

    Optional.ofNullable(person).ifPresent(p -> person.setAnswere(Answere.builder().answereId(code).answereComment(message).build()));

    return ResponseEntity.ok(Response
      .<ModelPersonAnswere>builder()
      .code(code)
      .message(message)
      .response(person)
      .build());
  }

}
