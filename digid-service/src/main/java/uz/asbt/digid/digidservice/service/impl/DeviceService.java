package uz.asbt.digid.digidservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.exception.RecognizeIntegrationException;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.digidservice.service.IDeviceService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

@Service
public class DeviceService implements IDeviceService {

  private final RestTemplate restTemplate;
  private final Environment env;
  private final MessageSource messageSource;

  @Autowired
  public DeviceService(final RestTemplate restTemplate,
                    final Environment env,
                    final MessageSource messageSource) {
    this.restTemplate = restTemplate;
    this.env = env;
    this.messageSource = messageSource;
  }

  @Override
  public DeviceDTO findBySerialNumber(final String serialNumber, final Locale locale) {
    try {
      final URI uri = new URI("http://crm-service/device/" + locale.getLanguage() + "/findBySerial/" + serialNumber);
      final HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
      final HttpEntity<?> httpEntity = new HttpEntity<>(headers);
      final ResponseEntity<Response<DeviceDTO>> responseEntity = restTemplate.exchange(
        uri,
        HttpMethod.GET,
        httpEntity,
        new ParameterizedTypeReference<Response<DeviceDTO>>() {}
      );
      if (responseEntity.getStatusCode() != HttpStatus.OK)
        throw new CustomException(
          env.getProperty("ex8887", Integer.class),
          messageSource.getMessage("message_8887", null, locale));
      final Response<DeviceDTO> response = responseEntity.getBody();
      if (response.getCode() != env.getProperty("ok", Integer.class))
        throw new CustomException(response.getCode(), response.getMessage());
      return response.getResponse();
    } catch (final URISyntaxException ex) {
      throw new RecognizeIntegrationException();
    }
  }
}
