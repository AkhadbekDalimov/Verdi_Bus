package uz.asbt.digid.crmservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.entity.Client;
import uz.asbt.digid.common.models.entity.Device;
import uz.asbt.digid.common.models.entity.auth.Login;
import uz.asbt.digid.common.models.rest.ReadlessClient;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.crmservice.exception.ProviderSaveException;
import uz.asbt.digid.crmservice.service.ClientService;
import uz.asbt.digid.crmservice.service.DeviceService;
import uz.asbt.digid.crmservice.service.LoginService;

import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/provider/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProviderController {

  private static final String SUCCESS = "message_0";

  ClientService service;
  DeviceService deviceService;
  LoginService loginService;
  MessageSource messageSource;
  ModelMapper modelMapper;

  @Transactional
  @PostMapping(value = "{lang}/save")
  public ResponseEntity<Response<Login>> save(@PathVariable("lang") final Locale locale,
                                              @RequestBody final ReadlessClient readlessClient) {
    return Optional.ofNullable(readlessClient)
      .map(client -> deviceService.findByCrmId(client.getDeviceCrmId()))
      .map(device -> {
        final Client client = readlessClient.buildClient();
        client.setDevice(modelMapper.map(device, Device.class));
        return client;
      })
      .map(client -> modelMapper.map(client, ClientDTO.class))
      .map(service::save)
      .map(dto -> modelMapper.map(dto, Client.class))
      .map(readlessClient::buildLogin)
      .map(loginService::save)
      .map(login -> Response.<Login>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(login)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(ProviderSaveException::new);
  }

  @PostMapping(value = "{lang}/update")
  public ResponseEntity<Response<Login>> update(@PathVariable("lang") final Locale locale,
                                                @RequestBody final ReadlessClient readlessClient) {
    return Optional.ofNullable(readlessClient)
      .map(client -> loginService.findByUsername(client.getUsername()))
      .map(loginService::update)
      .map(login -> Response.<Login>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(login)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(ProviderSaveException::new);
  }

}
