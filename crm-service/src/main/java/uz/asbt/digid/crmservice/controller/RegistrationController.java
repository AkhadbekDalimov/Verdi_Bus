package uz.asbt.digid.crmservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.crmservice.exception.ClientSaveException;
import uz.asbt.digid.crmservice.exception.FindBySerialNumberException;
import uz.asbt.digid.crmservice.exception.FindClientBySerialNumberException;
import uz.asbt.digid.crmservice.service.DeviceService;
import uz.asbt.digid.crmservice.service.impl.RegistrationService;
import uz.asbt.digid.crmservice.util.RendipScoresProvider;

import java.util.Locale;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/registration")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationController {

  private static final String SUCCESS = "message_0";

  DeviceService deviceService;
  MessageSource messageSource;
  final RegistrationService registrationService;
final RendipScoresProvider rendipScoresProvider;

  @GetMapping(value = "{lang}/find/{sn}")
  public ResponseEntity<Response<DeviceDTO>> find(@PathVariable("lang") final Locale locale,
                                                  @PathVariable("sn") final String sn) {
    return Optional.ofNullable(sn)
      .map(deviceService::findBySerialNumber)
      .map(device -> Response.<DeviceDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(device)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(() -> new FindBySerialNumberException(sn));
  }

  @GetMapping(value = "{lang}/findBySerial/{sn}")
  public ResponseEntity<Response<ClientDTO>> findClient(@PathVariable("lang") final Locale locale,
                                                        @PathVariable("sn") final String serialNumber) {
    return Optional.ofNullable(serialNumber)
      .map(registrationService::findClient)
      .map(client -> Response.<ClientDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(client)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(() -> new FindClientBySerialNumberException(serialNumber));
  }

  @PostMapping(value = "{lang}/register")
  public ResponseEntity<Response<ClientDTO>> register(@PathVariable("lang") final Locale locale,
                                                      @RequestBody final ClientDTO client) {
    return Optional.of(registrationService.register(client))
      .map(result -> ResponseEntity.ok(Response.<ClientDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(result)
        .build()))
      .orElseThrow(ClientSaveException::new);
  }

}
