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
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.models.rest.mip.GetAddressRequest;
import uz.asbt.digid.common.models.rest.mip.GetAddressResponse;
import uz.asbt.digid.digidservice.service.IntegrationService;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service("mipAddressIntegration")
public class MipAddressIntegration implements IntegrationService<ModelPersonAnswere, GetAddressResponse> {

    private final RestTemplate restTemplate;

    @Autowired
    public MipAddressIntegration(@Qualifier("mipAddressRestTemplate") final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GetAddressResponse get(final ModelPersonAnswere personAnswere) {
        final Response<GetAddressResponse> response ;
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final GetAddressRequest request = buildRequest(personAnswere);
        final HttpEntity<GetAddressRequest> requestEntity = new HttpEntity<>(request, headers);
        try {
            final URI uri = new URI("http://mip-service/mip/address/ru/info");

            final ResponseEntity<Response<GetAddressResponse>> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<GetAddressResponse>>() {});
            if (requestEntity.getBody() == null)
                throw new IntegrationException(
                        IntegrationException.COMMUNICATION_ERROR,
                        "Не удалось связаться с удаленным сервисом"
                );
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new IntegrationException(IntegrationException.COMMUNICATION_ERROR, "Не удалось связаться с удаленным сервисом");
            }
            response = responseEntity.getBody();
            log.info("----------------------------- Get Address Response: " + response);
            if (response.getCode() != 0) {
                throw new IntegrationException(response.getCode(), response.getMessage());
            }
        } catch (final URISyntaxException ex) {
            log.error(ex.getMessage());
            throw new IntegrationException(IntegrationException.URL_FORMAT_ERROR, ex.getMessage());
        }
        return response.getResponse();
    }

    private GetAddressRequest buildRequest(final ModelPersonAnswere personAnswere) {
        final GetAddressRequest request = new GetAddressRequest();
        request.setGuid(personAnswere.getRequestGuid());
        request.setSerialNumber(personAnswere.getModelServiceInfo().getServiceInfo().getScannerSerial());
        request.setPin(personAnswere.getModelPersonPassport().getPersonPassport().getPinpp());
        return request;
    }
}
