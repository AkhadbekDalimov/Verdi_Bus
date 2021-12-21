package uz.asbt.digid.crmservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.CrmMobileRegRequest;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.crmservice.exception.ClientSaveException;
import uz.asbt.digid.crmservice.service.DeviceService;
import uz.asbt.digid.crmservice.service.MobileClientService;

import java.util.Locale;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/mobile/")
public class MobileClientController {

  MobileClientService mobileClientService;
  DeviceService deviceService;
  MessageSource messageSource;
  ModelMapper modelMapper;
  private static final String SUCCESS = "message_0";

  @PostMapping(value = "{lang}/mobile")
  public Response<ClientDTO> saveMobileClientData(@PathVariable("lang") final Locale locale,
                                                                  @RequestBody final CrmMobileRegRequest regRequest) {
    log.info("CRM service mobile data controller:{}", regRequest);
    return Optional.ofNullable(regRequest)
      .map(mobileClientService::sendMobileClientDataToCrm)
      .map(c -> {
        final Response<ClientDTO> clientDTOResponse = Response.<ClientDTO>builder()
          .code(0)
          .message(messageSource.getMessage(SUCCESS, null, locale))
          .response(c)
          .build();
        log.info("mobile controller {}", clientDTOResponse.getResponse());
        return clientDTOResponse;
      })
      .orElseThrow(ClientSaveException::new);
  }
}