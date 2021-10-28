package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import uz.asbt.digid.common.models.rest.mip.TaxInfoRequest;
import uz.asbt.digid.common.models.rest.mip.TaxInfoResponse;
import uz.asbt.digid.digidservice.service.IntegrationService;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RequiredArgsConstructor
@Service("mipGniIntegration")
public class MipGniIntegration implements IntegrationService<ModelPersonAnswere, TaxInfoResponse> {

    @Qualifier("mipGniRestTemplate")
    private final RestTemplate restTemplate;

    @Override
    public TaxInfoResponse get(final ModelPersonAnswere personAnswere) {
        final Response<TaxInfoResponse> response;
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final TaxInfoRequest request = buildRequest(personAnswere);
        final HttpEntity<TaxInfoRequest> requestEntity = new HttpEntity<>(request, headers);
        try {
            final URI uri = new URI("http://mip-service/mip/gni/ru/infoByPin");

            final ResponseEntity<Response<TaxInfoResponse>> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TaxInfoResponse>>() {});
            if (requestEntity.getBody() == null)
                throw new IntegrationException(
                        IntegrationException.COMMUNICATION_ERROR,
                        "Не удалось связаться с удаленным сервисом"
                );

            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new IntegrationException(IntegrationException.COMMUNICATION_ERROR, "Не удалось связаться с удаленным сервисом");
            }
            response = responseEntity.getBody();
            log.info("----------------------------- Get GNI Response: " + response);
            if (response.getCode() != 0) {
                throw new IntegrationException(response.getCode(), response.getMessage());
            }
        } catch (final URISyntaxException ex) {
            log.error(ex.getMessage());
            throw new IntegrationException(IntegrationException.URL_FORMAT_ERROR, ex.getMessage());
        }
        return response.getResponse();
    }

    private TaxInfoRequest buildRequest(final ModelPersonAnswere personAnswere) {
        final TaxInfoRequest request = new TaxInfoRequest();
        request.setLang("1");
        request.setPin(personAnswere.getModelPersonPassport().getPersonPassport().getPinpp());
        request.setGuid(personAnswere.getRequestGuid());
        request.setSerialNumber(personAnswere.getModelServiceInfo().getServiceInfo().getScannerSerial());
        return request;
    }
}
