package uz.asbt.digid.digidservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.asbt.digid.common.exception.DataSaveException;
import uz.asbt.digid.common.exception.GettingListException;
import uz.asbt.digid.common.exception.RecognizeIntegrationException;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.digidservice.model.dto.MobileAppIdRequestDTO;
import uz.asbt.digid.digidservice.model.dto.MobileAppIdResponseDTO;
import uz.asbt.digid.digidservice.model.dto.MobileDataDTO;
import uz.asbt.digid.digidservice.service.MobileDataService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/mobile/data")
@RequiredArgsConstructor
public class MobileDataController {

  private static final String SUCCESS = "message_0";

  private final MobileDataService mobileDataService;
  private final MessageSource messageSource;

  @PostMapping(value = "/{lang}/add")
  public ResponseEntity<Response<MobileDataDTO>> addNewMobileData(@RequestHeader final HttpHeaders headers,
                                                               @RequestBody final MobileDataDTO mobileDataDTO,
                                                               @PathVariable("lang") final Locale locale) {
    return Optional.ofNullable(mobileDataDTO)
      .map(mobileDataService::saveMobileDataDTO)
      .map(mob -> Response.<MobileDataDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(mob)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(DataSaveException::new);
  }

  @GetMapping(value = "/{lang}/findMobileDatas")
  public ResponseEntity<Response<List<MobileDataDTO>>> responseResponseEntity(@RequestHeader final HttpHeaders headers,
                                                                            final String pinpp, @PathVariable final Locale locale){
    return
      Optional.ofNullable(pinpp)
      .map(mobileDataService::findAllByPinpp)
      .map(mobs -> Response.<List<MobileDataDTO>>builder()
      .code(0)
      .message(messageSource.getMessage(SUCCESS, null, locale))
      .response(mobs)
      .build()
      ).map(ResponseEntity::ok)
      .orElseThrow(GettingListException::new);
  }

  @PostMapping(value = "/{lang}/checkAppId")
  public ResponseEntity<Response<MobileAppIdResponseDTO>> checkAppId(@RequestHeader final HttpHeaders headers,
                                                                     @RequestBody final MobileAppIdRequestDTO requestDTO,
                                                                     @PathVariable("lang") final Locale locale) {
    return Optional.ofNullable(requestDTO)
            .map(mobileDataService::findAllByAppId)
            .map(mob -> Response.<MobileAppIdResponseDTO>builder()
              .code(0)
              .message(messageSource.getMessage(SUCCESS, null, locale))
              .response(mob)
              .build())
            .map(ResponseEntity::ok)
            .orElseThrow(RecognizeIntegrationException::new);
  }

}
