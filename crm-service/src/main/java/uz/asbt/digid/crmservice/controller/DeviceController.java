package uz.asbt.digid.crmservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
import uz.asbt.digid.common.exception.UniqueIdentificatorException;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.rest.GridRequest;
import uz.asbt.digid.common.models.rest.GridResponse;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.enums.DeviceStatus;
import uz.asbt.digid.crmservice.exception.DeviceActivationException;
import uz.asbt.digid.crmservice.exception.DeviceDeactivationException;
import uz.asbt.digid.crmservice.exception.DeviceSaveException;
import uz.asbt.digid.crmservice.exception.FindBySerialNumberException;
import uz.asbt.digid.crmservice.service.DeviceService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/device/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeviceController {

  DeviceService service;
  MessageSource messageSource;
  private static final String SUCCESS = "message_0";

  @PostMapping(value = "{lang}/save")
  public ResponseEntity<Response<DeviceDTO>> save(@PathVariable("lang") final Locale locale,
                                                  @RequestBody final DeviceDTO device) {
    log.info("Device from remote: {}", device);
    return Optional.ofNullable(device)
      .map(service::save)
      .map(d -> Response
        .<DeviceDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(d)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(DeviceSaveException::new);
  }

  @PostMapping(value = "{lang}/update")
  public ResponseEntity<Response<DeviceDTO>> update(@PathVariable("lang") final Locale locale,
                                                    @RequestBody final DeviceDTO device) {
    return Optional.ofNullable(device)
      .map(service::update)
      .map(d -> Response
        .<DeviceDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(d)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(DeviceSaveException::new);
  }

  @GetMapping(value = "{lang}/find/{id}")
  public ResponseEntity<Response<DeviceDTO>> find(@PathVariable("lang") final Locale locale,
                                                  @PathVariable("id") final Long id) {
    return Optional.ofNullable(id)
      .map(service::findById)
      .map(dev -> Response.<DeviceDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(dev)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(EntityFindException::new);
  }

  @PostMapping(value = "{lang}/findAll")
  public ResponseEntity<Response<GridResponse<List<DeviceDTO>>>> findAll(@PathVariable("lang") final Locale locale,
                                                                         @RequestBody final GridRequest gridRequest) {
    return Optional.ofNullable(gridRequest)
      .map(grid -> service.findAll(PageRequest.of(grid.getPage(), grid.getSize())))
      .map(list -> Response.<GridResponse<List<DeviceDTO>>>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(list)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(GettingListException::new);
  }

  @GetMapping(value = "{lang}/findBySerial/{sn}")
  public ResponseEntity<Response<DeviceDTO>> findBySerial(@PathVariable("lang") final Locale locale,
                                                          @PathVariable("sn") final String sn) {
    return Optional.ofNullable(sn)
      .map(service::findBySerialNumber)
      .map(device -> Response.<DeviceDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(device)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(() -> new FindBySerialNumberException(sn));
  }

  @PostMapping(value = "{lang}/activate")
  public ResponseEntity<Response<DeviceDTO>> activate(@PathVariable("lang") final Locale locale,
                                                      @RequestBody final DeviceDTO device) {
    return Optional.ofNullable(device)
      .filter(this::checkIdIsPresentOrThrow)
      .map(d -> {
        d.setStatus(DeviceStatus.ACTIVE.getStatus());
        return d;
      })
      .map(service::save)
      .map(d -> Response.<DeviceDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(d)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(DeviceActivationException::new);
  }

  @PostMapping(value = "{lang}/deactivate")
  public ResponseEntity<Response<DeviceDTO>> deactivate(@PathVariable("lang") final Locale locale,
                                                        @RequestBody final DeviceDTO device) {
    return Optional.ofNullable(device)
      .filter(this::checkIdIsPresentOrThrow)
      .map(d -> {
        d.setStatus(DeviceStatus.NOT_ACTIVE.getStatus());
        return d;
      })
      .map(service::save)
      .map(d -> Response.<DeviceDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(d)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(DeviceDeactivationException::new);
  }

  private boolean checkIdIsPresentOrThrow(final DeviceDTO device) {
    if (device.getId() != 0)
      return true;
    throw new UniqueIdentificatorException();
  }


}
