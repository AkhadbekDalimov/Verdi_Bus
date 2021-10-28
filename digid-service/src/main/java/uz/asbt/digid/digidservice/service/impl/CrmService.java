package uz.asbt.digid.digidservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
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
import uz.asbt.digid.common.exception.IntegrationException;
import uz.asbt.digid.common.exception.RecognizeIntegrationException;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.CrmMobileRegRequest;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.digidservice.service.ICrmService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

@Service
@Slf4j
public class CrmService implements ICrmService {

    private final RestTemplate restTemplate;
    private final Environment env;
    private final MessageSource messageSource;

    @Autowired
    public CrmService(final RestTemplate restTemplate,
                      final Environment env,
                      final MessageSource messageSource) {
        this.restTemplate = restTemplate;
        this.env = env;
        this.messageSource = messageSource;
    }

    @Override
    public ClientDTO findBySerialNumber(final String serialNumber, final Locale locale) {
        try {
            final URI uri = new URI("http://crm-service/client/" + locale.getLanguage() + "/findBySerial/" + serialNumber);
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            final HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            final ResponseEntity<Response<ClientDTO>> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    httpEntity,
                    new ParameterizedTypeReference<Response<ClientDTO>>() {}
            );
            if (responseEntity.getStatusCode() != HttpStatus.OK)
                throw new CustomException(
                        env.getProperty("ex8887", Integer.class),
                        messageSource.getMessage("message_8887", null, locale));
            final Response<ClientDTO> response = responseEntity.getBody();
            if (response.getCode() != env.getProperty("ok", Integer.class))
                throw new CustomException(response.getCode(), response.getMessage());
            return response.getResponse();
        } catch (final URISyntaxException ex) {
            throw new RecognizeIntegrationException();
        }
    }

    @Override
    public ClientDTO findByMacAddressAndDevice(final String macAddress, final String serialNumber, final Locale locale)  {
        try {
            final URI uri = new URI("http://crm-service/client/" + locale.getLanguage() + "/findByMacAddress/"
              + macAddress + "/serialNumber/" + serialNumber);
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            final HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            final ResponseEntity<Response<ClientDTO>> responseEntity = restTemplate.exchange(
              uri,
              HttpMethod.GET,
              httpEntity,
              new ParameterizedTypeReference<Response<ClientDTO>>() {}
            );
            if (responseEntity.getStatusCode() != HttpStatus.OK)
                throw new CustomException(
                  env.getProperty("ex8887", Integer.class),
                  messageSource.getMessage("message_8887", null, locale));
            final Response<ClientDTO> response = responseEntity.getBody();
            if (response.getCode() != env.getProperty("ok", Integer.class))
                throw new CustomException(response.getCode(), response.getMessage());
            return response.getResponse();
        } catch (final URISyntaxException ex) {
            throw new RecognizeIntegrationException();
        }
    }

    @Override
    public ClientDTO findByMacAddress(final String macAddress, final Locale locale) throws URISyntaxException {
        final URI uri = new URI("http://crm-service/client/" + locale.getLanguage() + "/findByMacAddress/" + macAddress);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        final ResponseEntity<Response<ClientDTO>> responseEntity = restTemplate.exchange(
          uri,
          HttpMethod.GET,
          httpEntity,
          new ParameterizedTypeReference<Response<ClientDTO>>() {}
        );
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new CustomException(
              env.getProperty("ex8887", Integer.class),
              messageSource.getMessage("message_8887", null, locale));
        final Response<ClientDTO> response = responseEntity.getBody();
        if (response.getCode() != env.getProperty("ok", Integer.class))
            throw new CustomException(response.getCode(), response.getMessage());
        return response.getResponse();
    }

    @Override
    public ClientDTO sendMobileClientDataToCrm(final CrmMobileRegRequest crmMobileRegRequest, final Locale locale) {
        final Response<ClientDTO> response;
        try {

            log.info("mobile data registration request {}", crmMobileRegRequest);

            final URI uri = new URI("http://crm-service/mobile/" + locale.getLanguage() + "/mobile/");
            log.info("----------------------------- URI: " + uri.toString());

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            final HttpEntity<CrmMobileRegRequest> requestEntity = new HttpEntity<>(crmMobileRegRequest, headers);

            final ResponseEntity<Response<ClientDTO>> responseEntity = restTemplate.exchange(
              uri,
              HttpMethod.POST,
              requestEntity, new ParameterizedTypeReference<Response<ClientDTO>>() {}
            );
            final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            final String json = ow.writeValueAsString(responseEntity);
            log.info("mobile data registration request json data 2{}", json);
//            if (responseEntity.getStatusCode() != HttpStatus.OK) {
//                throw new Exception(responseEntity.getStatusCode().getReasonPhrase());
//            }

            if (responseEntity.getStatusCode() != HttpStatus.OK)
                throw new CustomException(
                  env.getProperty("ex8887", Integer.class),
                  messageSource.getMessage("message_8887", null, locale));
            response = responseEntity.getBody();
            if (response.getCode() != env.getProperty("ok", Integer.class))
                throw new CustomException(response.getCode(), response.getMessage());
            return response.getResponse();
        } catch (final Exception ex) {
            throw new IntegrationException(IntegrationException.COMMUNICATION_ERROR, ex.getMessage());
        }
    }
}
