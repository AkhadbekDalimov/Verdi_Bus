package uz.asbt.digid.crmservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.asbt.digid.common.enums.NeedSend;
import uz.asbt.digid.common.enums.SendStatus;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.CrmMobileRegRequest;
import uz.asbt.digid.common.models.dto.CrmMobileRegResponse;
import uz.asbt.digid.common.models.entity.Client;
import uz.asbt.digid.common.models.entity.ClientSended;
import uz.asbt.digid.crmservice.exception.CrmException;
import uz.asbt.digid.crmservice.exception.TokenException;
import uz.asbt.digid.crmservice.model.CrmResponse;
import uz.asbt.digid.crmservice.model.ReportResponse;
import uz.asbt.digid.crmservice.model.TokenRequest;
import uz.asbt.digid.crmservice.model.TokenResponse;
import uz.asbt.digid.crmservice.service.ClientSendService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CRMIntegrationService {

  @Value("${crm.integration.client-url}")
  String crmURL;

  @Value("${crm.integration.client-token-url}")
  String tokenCrmURL;

  @Value("${crm.integration.report-url}")
  String crmReportURL;

  @Value("${crm.integration.mobile-register-url}")
  String crmMobileRegisterURL;

  @Value("${crm.integration.username}")
  String username;

  @Value("${crm.integration.password}")
  String password;

  final RestTemplate restTemplate;
  final ClientSendService sendService;
  final ModelMapper mapper;

  public void sendToCRM(final ClientSended sended) throws CrmException, TokenException {
    final HttpHeaders headers = token();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    final HttpEntity<Client> requestEntity = new HttpEntity<>(sended.getClient(), headers);

    log.info("Begin send request to save client in CRM: {}", sended.getClient().toString());
    final ResponseEntity<CrmResponse> responseEntity = restTemplate.exchange(
      crmURL,
      HttpMethod.POST,
      requestEntity,
      CrmResponse.class
    );
    log.info("End send request to save client in CRM");

    final CrmResponse crmResponse = responseEntity.getBody();

    if (crmResponse == null)
      throw new IllegalArgumentException("Crm response is null");

    log.info("Crm response: {}", crmResponse.toString());

    if (crmResponse.getCode() != 0)
      throw new CrmException(crmResponse.getCode(), crmResponse.getText());

    final ClientSended result = sendService.save(mapper.map(sended.getClient(), ClientDTO.class),
      SendStatus.SEND.getStatus(), NeedSend.NOT_SEND);
    if (null == result)
      throw new IllegalArgumentException(String.format("Can't update client id: %d", sended.getClient().getId()));
    sendService.updateStatus(sended);

  }

  private HttpHeaders token() throws TokenException {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

    final TokenRequest tokenRequest = new TokenRequest();
    tokenRequest.setUsername(username);
    tokenRequest.setPassword(password);

    final HttpEntity<TokenRequest> requestEntity = new HttpEntity<>(tokenRequest, headers);

    log.info("Begin send request to get token: {}", tokenRequest.toString());
    final ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(
      tokenCrmURL,
      HttpMethod.POST,
      requestEntity,
      TokenResponse.class
    );
    log.info("End send request to get token");

    final TokenResponse tokenResponse = responseEntity.getBody();

    if (tokenResponse == null)
      throw new IllegalArgumentException("Token response is null");

    log.info("Token response: {}", tokenResponse.toString());

    if (tokenResponse.getCode() != 0)
      throw new TokenException(
        tokenResponse.getCode(), tokenResponse.getMessage(), tokenResponse.getException()
      );

    final HttpHeaders httpHeaders = new HttpHeaders();

    final List<String> cookies = responseEntity.getHeaders().get(HttpHeaders.SET_COOKIE);

    if (cookies == null)
      throw new IllegalArgumentException("Cookies is null");

    for (final String c : cookies) {
      final String[] split = c.split(";");
      final String[] strings = split[0].split("=");
      log.info(String.format("Key: %s, Value: %s", strings[0], strings[1]));
      httpHeaders.set(strings[0], strings[1]);
    }

    return httpHeaders;
  }

  public void sendReportToCRM(final ReportResponse response) throws CrmException, TokenException {
    final HttpHeaders headers = token();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    final HttpEntity<ReportResponse> requestEntity = new HttpEntity<>(response, headers);

    log.info("Begin send report response to CRM: {}", response.toString());
    final ResponseEntity<CrmResponse> responseEntity = restTemplate.exchange(
            crmReportURL,
            HttpMethod.POST,
            requestEntity,
            CrmResponse.class
    );
    log.info("End send request to save client in CRM");

    final CrmResponse crmResponse = responseEntity.getBody();

    if (crmResponse == null)
      throw new IllegalArgumentException("Crm response is null");

    log.info("Crm response: {}", crmResponse.toString());
  }

  public CrmMobileRegResponse sendMobileDataToCRM(final CrmMobileRegRequest mobClientData) throws TokenException {
    final HttpHeaders headers = token();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    final HttpEntity<CrmMobileRegRequest> requestEntity = new HttpEntity<>(mobClientData, headers);
    log.info("Begin sending mobile data to CRM:{}", requestEntity);
    final ResponseEntity<CrmMobileRegResponse> responseEntity = restTemplate.exchange(
      crmMobileRegisterURL,
      HttpMethod.POST,
      requestEntity,
      CrmMobileRegResponse.class
    );
    log.info("End sending mobile data to CRM");
    final CrmMobileRegResponse crmResponse = responseEntity.getBody();

    if (crmResponse == null)
      throw new IllegalArgumentException("Crm response is null");

    log.info("Crm response: {}", crmResponse.toString());

    return crmResponse;
  }

}
