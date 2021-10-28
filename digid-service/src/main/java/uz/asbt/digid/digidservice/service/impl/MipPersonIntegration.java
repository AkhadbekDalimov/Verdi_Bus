package uz.asbt.digid.digidservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.asbt.digid.common.exception.IntegrationException;
import uz.asbt.digid.common.models.entity.RequestMonitor;
import uz.asbt.digid.common.models.entity.ResponseMonitor;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.models.rest.mip.PersonInfoRequest;
import uz.asbt.digid.common.models.rest.mip.PersonInfoResponse;
import uz.asbt.digid.common.service.monitor.IMonitorReport;
import uz.asbt.digid.digidservice.service.IntegrationService;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service("mipPersonIntegration")
public class MipPersonIntegration implements IntegrationService<ModelPersonAnswere, PersonInfoResponse> {

  private final RestTemplate restTemplate;
  private final IMonitorReport<RequestMonitor, ResponseMonitor> monitor;

  @Autowired
  public MipPersonIntegration(final RestTemplate restTemplate,
                              @Qualifier("monitorService") final IMonitorReport<RequestMonitor, ResponseMonitor> monitor) {
    this.restTemplate = restTemplate;
    this.monitor = monitor;
  }

  @Override
  public PersonInfoResponse get(final ModelPersonAnswere personAnswere) {
    final Response<PersonInfoResponse> response;
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    final PersonInfoRequest request = buildRequest(personAnswere);
    final HttpEntity<PersonInfoRequest> requestEntity = new HttpEntity<>(request, headers);
    try {
      final URI uri = new URI("http://mip-service/mip/person/ru/info");
      final ResponseEntity<Response<PersonInfoResponse>> responseEntity = restTemplate.exchange(
        uri,
        HttpMethod.POST,
        requestEntity,
        new ParameterizedTypeReference<Response<PersonInfoResponse>>() {
        });
      if (requestEntity.getBody() == null)
        throw new IntegrationException(
          IntegrationException.COMMUNICATION_ERROR,
          "Не удалось связаться с удаленным сервисом"
        );

      if (responseEntity.getStatusCode() != HttpStatus.OK) {
        throw new IntegrationException(IntegrationException.COMMUNICATION_ERROR, "Не удалось связаться с удаленным сервисом");
      }
      response = responseEntity.getBody();
      log.info("----------------------------- Get Person Response: " + response);
      if (response.getCode() != 0) {
        throw new IntegrationException(response.getCode(), response.getMessage());
      }
      //Монитор ответа от сервиса НИББД
      logResponse(responseEntity.getBody(), personAnswere.getRequestGuid());
    } catch (final URISyntaxException ex) {
      log.error(ex.getMessage());
      throw new IntegrationException(IntegrationException.URL_FORMAT_ERROR, ex.getMessage());
    }
    return response.getResponse();
  }

  private PersonInfoRequest buildRequest(final ModelPersonAnswere personAnswere) {
    final PersonInfoRequest request = new PersonInfoRequest();
    request.setDocument(personAnswere.getModelPersonPassport().getPersonPassport().getDocumentNumber());
    request.setLangId("1");
    request.setPinpp(personAnswere.getModelPersonPassport().getPersonPassport().getPinpp());
    request.setGuid(personAnswere.getRequestGuid());
    request.setSerialNumber(personAnswere.getModelServiceInfo().getServiceInfo().getScannerSerial());
    return request;
  }

  private void logRequest(final ModelPersonAnswere request, final String photo) {
    log.info("Begin create monitor request");
    monitor.saveRequest(RequestMonitor
      .builder()
      .guid(request.getRequestGuid())
      .serialNumber(request.getModelServiceInfo().getServiceInfo().getScannerSerial())
      .pinpp(request.getModelPersonPassport().getPersonPassport().getPinpp())
      .documentNumber(request.getModelPersonPassport().getPersonPassport().getDocumentNumber())
      .requestDate(LocalDateTime.now(ZoneId.of("Asia/Tashkent")))
      .photo(photo)
      .build());
    log.info("End create monitor request");
  }

  private void logResponse(final Response<PersonInfoResponse> response, final String guid) {
    log.info("Begin create monitor response");
    monitor.saveResponse(ResponseMonitor
      .builder()
      .guid(guid)
      .code(String.valueOf(response.getCode()))
      .message(response.getMessage())
      .responseDate(LocalDateTime.now(ZoneId.of("Asia/Tashkent")))
      .build());
    log.info("End create monitor response");
  }
}
