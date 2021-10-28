package uz.asbt.digid.crmservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.crmservice.exception.CrmException;
import uz.asbt.digid.crmservice.exception.ReportException;
import uz.asbt.digid.crmservice.exception.TokenException;
import uz.asbt.digid.crmservice.model.ReportRequest;
import uz.asbt.digid.crmservice.model.ReportResponse;
import uz.asbt.digid.crmservice.service.ReportService;
import uz.asbt.digid.crmservice.service.impl.CRMIntegrationService;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/report/")
public class ReportController {

  private static final String SUCCESS = "message_0";

  private final ReportService service;
  private final MessageSource messageSource;
  private final CRMIntegrationService crmIntegrationService;

  @PostMapping(value = "{lang}/info")
  public ResponseEntity<Response<ReportResponse>> info(
    @RequestBody final ReportRequest request,
    @PathVariable("lang") final Locale locale) {
    return Optional.ofNullable(request)
             .map(service::info)
             .map(info -> Response.<ReportResponse>builder()
                            .code(0)
                            .message(messageSource.getMessage(SUCCESS, null, locale))
                            .response(info)
                            .build())
             .map(ResponseEntity::ok)
             .orElseThrow(ReportException::new);
  }

  @PostMapping(value = "{lang}/async/info")
  public ResponseEntity<Response<ReportResponse>> asyncInfo(
          @RequestBody final ReportRequest request,
          @PathVariable("lang") final Locale locale) {
    CompletableFuture.supplyAsync(() -> service.info(request))
            .thenAccept((res -> {
              try {
                crmIntegrationService.sendReportToCRM(res);
              } catch (CrmException | TokenException e) {
                log.error(e.getMessage());
              }
            }));
    return ResponseEntity.ok(Response.<ReportResponse>builder().code(0).message("OK").response(null).build());
  }

  @PostMapping(value = "{lang}/liveness/info")
  public ResponseEntity<Response<ReportResponse>> livenessInfo(
          @RequestBody final ReportRequest request,
          @PathVariable("lang") final Locale locale) {
    return Optional.ofNullable(request)
            .map(service::livenessReport)
            .map(info -> Response.<ReportResponse>builder()
                    .code(0)
                    .message(messageSource.getMessage(SUCCESS, null, locale))
                    .response(info)
                    .build())
            .map(ResponseEntity::ok)
            .orElseThrow(ReportException::new);
  }

}
