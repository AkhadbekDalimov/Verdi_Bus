package uz.asbt.digid.crmservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.common.exception.GettingListException;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.rest.GridRequest;
import uz.asbt.digid.common.models.rest.GridResponse;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.crmservice.exception.ClientFindByIdException;
import uz.asbt.digid.crmservice.exception.ClientSaveException;
import uz.asbt.digid.crmservice.service.ClientService;
import uz.asbt.digid.crmservice.service.DeviceService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/client/")
public class ClientController {

  ClientService service;
  DeviceService deviceService;
  MessageSource messageSource;
  ModelMapper modelMapper;

  private static final String SUCCESS = "message_0";

  @PostMapping(value = "{lang}/save")
  public ResponseEntity<Response<ClientDTO>> save(@PathVariable("lang") final Locale locale,
                                                  @RequestBody final ClientDTO client) {
    return Optional.ofNullable(client)
      .map(service::save)
      .map(c -> Response.<ClientDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(c)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(ClientSaveException::new);
  }

  @GetMapping(value = "{lang}/find/{id}")
  public ResponseEntity<Response<ClientDTO>> find(@PathVariable("lang") final Locale locale,
                                                  @PathVariable("id") final Long id) {
    return Optional.ofNullable(id)
      .map(service::findById)
      .map(c -> Response.<ClientDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(c)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(() -> new ClientFindByIdException(id));
  }

  @GetMapping(value = "{lang}/findByOrgCrmId/{orgCrmId}")
  public ResponseEntity<Response<ClientDTO>> findByOrgCrmId(@PathVariable("lang") final Locale locale,
                                                            @PathVariable("orgCrmId") final String orgCrmId) {
    return Optional.ofNullable(orgCrmId)
      .map(deviceService::findByOrgCrmId)
      .map(device -> modelMapper.map(device, DeviceDTO.class))
      .map(service::findByDevice)
      .map(client -> Response.<ClientDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(client)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(EntityFindException::new);
  }

  @PostMapping(value = "{lang}/findAll")
  public ResponseEntity<Response<GridResponse<List<ClientDTO>>>> findAll(@PathVariable("lang") final Locale locale,
                                                                         @RequestBody final GridRequest gridRequest) {
    return Optional.ofNullable(gridRequest)
      .map(grid -> PageRequest.of(gridRequest.getPage(), gridRequest.getSize()))
      .map(service::findAll)
      .map(list -> Response.<GridResponse<List<ClientDTO>>>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(list)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(GettingListException::new);
  }

  @GetMapping(value = "{lang}/findBySerial/{sn}")
  public ResponseEntity<Response<ClientDTO>> findBySerial(@PathVariable("lang") final Locale locale,
                                                          @PathVariable("sn") final String sn) {
    return Optional.ofNullable(sn)
      .map(deviceService::findBySerialNumber)
      .map(device -> modelMapper.map(device, DeviceDTO.class))
      .map(service::findByDevice)
      .map(client -> Response.<ClientDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(client)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(EntityFindException::new);
  }

  @GetMapping(value = "{lang}/findByMacAddress/{mac}")
  public ResponseEntity<Response<ClientDTO>> findByMacAddress(@PathVariable("lang") final Locale locale,
                                                              @PathVariable("mac") final String mac) {
    return Optional.ofNullable(mac)
      .flatMap(service::findByMacAddress)
      .map(client -> modelMapper.map(client, ClientDTO.class))
      .map(client -> Response.<ClientDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(client)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(EntityFindException::new);
  }

  @GetMapping(value = "{lang}/findByMacAddress/{mac}/serialNumber/{serialNumber}")
  public ResponseEntity<Response<ClientDTO>> findByMacAddressAndSerialNumber(@PathVariable("lang") final Locale locale,
                                                                             @PathVariable("mac") final String mac,
                                                                             @PathVariable("serialNumber") final String serialNumber) {
    return Optional.ofNullable(mac)
      .flatMap(m -> service.findByMacAddressAndSerialNumber(mac, serialNumber))
      .map(client -> modelMapper.map(client, ClientDTO.class))
      .map(client -> Response.<ClientDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(client)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(EntityFindException::new);
  }

}
