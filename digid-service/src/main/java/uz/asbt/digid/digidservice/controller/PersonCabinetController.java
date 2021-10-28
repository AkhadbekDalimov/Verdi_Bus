package uz.asbt.digid.digidservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.common.models.dto.PersonDocReadData;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.GridResponse;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.service.ErrorService;
import uz.asbt.digid.common.service.monitor.IReport;
import uz.asbt.digid.digidservice.model.dto.AllDocReadsRequest;
import uz.asbt.digid.digidservice.model.dto.InnDTO;
import uz.asbt.digid.digidservice.model.dto.PassportDataDTO;
import uz.asbt.digid.digidservice.service.InnService;
import uz.asbt.digid.digidservice.service.PassportDataService;
import uz.asbt.digid.digidservice.util.Utils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/cabinet")
@RequiredArgsConstructor
public class PersonCabinetController {

  private final PassportDataService passportDataService;
  private final MessageSource messageSource;
  @Qualifier("monitorService")
  private final IReport monitorService;
  private final InnService innService;
  private final ErrorService error;
  private static final String SUCCESS = "message_0";
  private static final String COLUMN_ID = "id";
  private final Utils utils;

  @PostMapping(value = "/{lang}/findAllPassportData")
  @SneakyThrows
  public ResponseEntity<Response<List<PassportDataDTO>>> passportDataByPinpp(@RequestHeader final HttpHeaders headers,
                                                                             @PathVariable("lang") final Locale locale,
                                                                             @RequestBody final ModelPersonAnswere person) {
    return Optional.ofNullable(person)
      .map(p -> utils.getPersonPinpp(person, locale))
      .map(passportDataService::findPassportDatasByPinpp)
      .map(list -> Response.<List<PassportDataDTO>>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(list)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(() -> {
        throw new CustomException(error.getErrorCode("ex9015"), error.getErrorMessage("message_9015"));
      });
  }

  @PostMapping(value = "/{lang}/findPassportDataByDocNumber")
  @SneakyThrows
  public ResponseEntity<Response<PassportDataDTO>> passportDataByDocNumber(@RequestHeader final HttpHeaders headers,
                                                                           @PathVariable("lang") final Locale locale,
                                                                           @RequestBody final ModelPersonAnswere person) {
    return Optional.ofNullable(person)
      .map(p -> utils.getPersonDocumentNumberByRequestMonitor(person, locale))
      .map(b -> {
        final Optional<PassportDataDTO> passportDataDTO = passportDataService.findPassportBySerialNumber(b);
        return passportDataDTO;
      })
      .map(obj -> Response.<PassportDataDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(obj
          .orElseThrow(EntityFindException::new))
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(() -> {
        throw new CustomException(error.getErrorCode("ex9015"), error.getErrorMessage("message_9015"));
      });
  }

  @PostMapping(value = "{lang}/findAllReads")
  @SneakyThrows
  public ResponseEntity<Response<GridResponse<List<PersonDocReadData>>>> findAll(@PathVariable("lang") final Locale locale,
                                                                                 @RequestBody final AllDocReadsRequest docReadsRequest) {
    return Optional.ofNullable(docReadsRequest.getGridRequest())
      .map(grid -> PageRequest.of(grid.getPage(), grid.getSize(), Sort.by(Direction.DESC, COLUMN_ID)))
      .map(pageable -> monitorService.findAllByDocumentNumber(pageable, utils.getPersonDocumentNumberByRequestMonitor(docReadsRequest.getPerson(), locale)))
      .map(list -> Response.<GridResponse<List<PersonDocReadData>>>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(list)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(() -> {
        throw new CustomException(error.getErrorCode("ex9015"), error.getErrorMessage("message_9015"));
      });
  }

  @PostMapping(value = "{lang}/getInn")
  @SneakyThrows
  public ResponseEntity<Response<InnDTO>> getInn(@PathVariable("lang") final Locale locale,
                                                 @RequestBody final ModelPersonAnswere person) {
    return Optional.ofNullable(person)
      .map(p -> utils.getPersonPinpp(person, locale))
      .map(innService::findByPinpp)
      .map(i -> Response.<InnDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(i)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(() -> {
        throw new CustomException(error.getErrorCode("ex9015"), error.getErrorMessage("message_9015"));
      });
  }
}
