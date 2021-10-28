package uz.asbt.digid.digidservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.asbt.digid.common.exception.IntegrationException;
import uz.asbt.digid.common.exception.NibbdException;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.NibbdRequest;
import uz.asbt.digid.common.models.rest.PhysicalPhotoResponse;
import uz.asbt.digid.common.models.rest.PhysicalRequest;
import uz.asbt.digid.digidservice.service.IntegrationService;

import java.net.URI;

@Slf4j
@RefreshScope
@Service("nibbdPhysicalPhoto")
public class NibbdPhysicalPhoto implements IntegrationService<ModelPersonAnswere, PhysicalPhotoResponse> {

    @Value("${integration.nibbd-url-physical-photo}")
    private String urlPhyscalPhoto;
    @Value("${nibbd.bank-code}")
    private String bankCode;
    @Value("${nibbd.bank.branch}")
    private String bankBranch;
    private final RestTemplate restTemplate;

    public NibbdPhysicalPhoto(@Qualifier("integrationRestTemplate") final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public PhysicalPhotoResponse get(final ModelPersonAnswere request) {
        final PhysicalPhotoResponse response;
        try {
            log.info("Request to parse: {}", request);
            final PhysicalRequest physicalRequest = buildNibbdRequest(request);
            log.info("Physical request {}", physicalRequest);
            final URI uri = new URIBuilder(urlPhyscalPhoto).build();
            log.info("----------------------------- URI: " + uri.toString());
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            final HttpEntity<PhysicalRequest> requestEntity = new HttpEntity<>(physicalRequest, headers);
            final ResponseEntity<PhysicalPhotoResponse> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    requestEntity,
                    PhysicalPhotoResponse.class
            );
            if (responseEntity.getStatusCode() != org.springframework.http.HttpStatus.OK) {
                throw new Exception(responseEntity.getStatusCode().getReasonPhrase());
            }
            response = responseEntity.getBody();
            log.info("----------------------------- PhysicalResponse: " + response);
        } catch (final NibbdException ex) {
            throw new IntegrationException(Integer.valueOf(ex.getCode()), ex.getMessage());
        } catch (final Exception ex) {
            throw new IntegrationException(IntegrationException.COMMUNICATION_ERROR, ex.getMessage());
        }
        return response;
    }

    private PhysicalRequest buildNibbdRequest(final ModelPersonAnswere personAnswere) {
        log.info("Begin build NIBBD request photo");
        final NibbdRequest nibbdRequest = NibbdRequest.NibbdRequestBuilder.build(personAnswere, bankCode, bankBranch);
        final PhysicalRequest physicalRequest = new PhysicalRequest();
        physicalRequest.setHeader(nibbdRequest.getHeader());
        physicalRequest.setBody(nibbdRequest.getBody());
        physicalRequest.setGuid(nibbdRequest.getGuid());
        physicalRequest.setSerialNumber(nibbdRequest.getSerialNumber());
        log.info("End build NIBBD request: {}", physicalRequest.toString());
        return physicalRequest;
    }
}
