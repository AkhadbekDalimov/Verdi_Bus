package uz.asbt.digid.digidservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.enums.PhoneVerificationStatus;
import uz.asbt.digid.common.exception.DataSaveException;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.service.ErrorService;
import uz.asbt.digid.digidservice.model.dto.PhoneVerificationRequestDTO;
import uz.asbt.digid.digidservice.service.IFacadePhoneVerificationService;
import uz.asbt.digid.digidservice.validator.PhoneValidation;

import java.util.Locale;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/phone")
public class PhoneVerificationController {

  private static ThreadLocal<Locale> localeThreadLocal = new ThreadLocal<>();
  private final IFacadePhoneVerificationService verification;
  private final PhoneValidation validation;
  private final ErrorService errorService;
  private final MessageSource messageSource;

  @PostMapping(value = "/{lang}/send")
  public ResponseEntity<Response<String>> sendSmsCode(@RequestBody final PhoneVerificationRequestDTO requestDTO,
                                                      @PathVariable("lang") final Locale locale) {
    localeThreadLocal.set(locale);
    log.info("send sms {}", requestDTO.getPhoneNumber());
    if(validation.isPhoneRegexpValid(requestDTO.getPhoneNumber())){
      return buildResponse(verification.sendSmsToPhone(requestDTO.getPhoneNumber()),
        requestDTO.getPhoneNumber(), locale);
    } else {
      log.info("phone number not valid {}", requestDTO);
      return buildResponse(PhoneVerificationStatus.PHONE_NUMBER_NOT_VALID.getStatus(),
        requestDTO.getPhoneNumber(), locale);
    }
  }

  @PostMapping(value = "/{lang}/check")
  public ResponseEntity<Response<String>> validateCode(@RequestBody final PhoneVerificationRequestDTO requestDTO,
                                                       @PathVariable("lang") final Locale locale) {
    log.info("send code and phone {}", requestDTO);
    if(validation.isPhoneRegexpValid(requestDTO.getPhoneNumber())){
      return buildResponse(verification.checkSmsCodeAndPhone(requestDTO.getPhoneNumber(), requestDTO.getSmsCode()),
        requestDTO.getPhoneNumber(), locale);
    } else {
      log.info("phone number not valid to verify {}", requestDTO);
      return buildResponse(PhoneVerificationStatus.PHONE_NUMBER_NOT_VALID.getStatus(),
        requestDTO.getPhoneNumber(), locale);
    }
  }

  private ResponseEntity<Response<String>> buildResponse(final int status,
                                                         final String phoneNumber,
                                                         final Locale locale) {
    log.info("create response with status to phone number {} {}", status, phoneNumber);
    return Optional.ofNullable(phoneNumber)
      .map(mob -> Response.<String>builder()
        .code(errorService.getErrorCode("ex" + status))
        .message(messageSource.getMessage("message_" + status, null, locale))
        .response(phoneNumber)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(DataSaveException::new);
  }

  public static Locale getLocaleThreadLocal() {
    return localeThreadLocal.get();
  }
}
