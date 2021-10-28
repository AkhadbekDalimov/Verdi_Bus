package uz.asbt.digid.digidservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.models.dto.LivenessResult;
import uz.asbt.digid.common.models.dto.SimilarityResult;
import uz.asbt.digid.common.models.dto.ValidateRequest;
import uz.asbt.digid.common.models.dto.ValidateResponse;
import uz.asbt.digid.common.models.entity.client.Answere;
import uz.asbt.digid.common.models.entity.client.LivenessAnswere;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.service.ErrorService;

import java.net.URI;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LivenessService {

  private final RestTemplate restTemplate;
  private final ErrorService error;

  @Value("${auth.username}")
  private String username;
  @Value("${auth.password}")
  private String password;

  public LivenessAnswere livenessResult(final ModelPersonAnswere personAnswere) {
    LivenessAnswere answere;
    try {
      final Locale locale = LocaleContextHolder.getLocale();
      final URI uri = new URI("http://liveness-service/recognize/" + locale.getLanguage() + "/validate/");
      final HttpEntity<ValidateRequest> httpEntity = getValidateRequestHttpEntity(personAnswere);
      final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
      try {
        final String json = ow.writeValueAsString(httpEntity.getBody());
        log.info("mobile data registration request json data3 {}", json);
      } catch (final JsonProcessingException ex){

      }
      final ResponseEntity<Response<ValidateResponse>> responseEntity = restTemplate.exchange(
        uri,
        HttpMethod.POST,
        httpEntity,
        new ParameterizedTypeReference<Response<ValidateResponse>>() {}
      );
      if (responseEntity.getStatusCode() != HttpStatus.OK)
        throw new CustomException(error.getErrorCode("ex8887"), error.getErrorMessage("message_8887"));
      answere = Optional.ofNullable(responseEntity.getBody())
        .filter(response -> response.getCode() == 0)
        .map(Response::getResponse)
        .map(result -> LivenessAnswere
          .builder()
          .answere(Answere.builder().answereId(error.getErrorCode("ok")).answereComment(error.getErrorMessage("message_0")).build())
          .validateResponse(result)
          .build())
        .orElseThrow(() -> new CustomException(responseEntity.getBody().getCode(), responseEntity.getBody().getMessage()));
    } catch (final CustomException ex) {
      answere = LivenessAnswere.builder()
        .answere(Answere.builder().answereId(ex.getCode()).answereComment(ex.getInfoMessage()).build())
        .validateResponse(null)
        .build();
    } catch (final Exception ex) {
      log.error("livenessResult Exception" + ex.getMessage());
      answere = LivenessAnswere.builder()
        .answere(Answere.builder().answereId(error.getErrorCode("ex9000")).answereComment(error.getErrorMessage("message_9000")).build())
        .validateResponse(null)
        .build();
    }
    if(personAnswere.getLivenessAnswere() != null) {
      personAnswere.getLivenessAnswere().setValidateResponse(answere.getValidateResponse());
      personAnswere.getLivenessAnswere().setAnswere(answere.getAnswere());
    } else {
      personAnswere.setLivenessAnswere(answere);
    }
    return answere;
  }

  public LivenessAnswere checkLiveness(final ModelPersonAnswere personAnswere) {
    LivenessAnswere answere;
    try {
      final Locale locale = LocaleContextHolder.getLocale();
      final URI uri = new URI("http://liveness-service/recognize/" + locale.getLanguage() + "/liveness/");
      final HttpEntity<ValidateRequest> httpEntity = getValidateRequestHttpEntity(personAnswere);
      final ResponseEntity<Response<LivenessResult>> responseEntity = restTemplate.exchange(
        uri,
        HttpMethod.POST,
        httpEntity,
        new ParameterizedTypeReference<Response<LivenessResult>>() {}
      );
      if (responseEntity.getStatusCode() != HttpStatus.OK)
        throw new CustomException(error.getErrorCode("ex8887"), error.getErrorMessage("message_8887"));
      answere = Optional.ofNullable(responseEntity.getBody())
        .filter(response -> response.getCode() == 0)
        .map(Response::getResponse)
        .map(result -> LivenessAnswere
          .builder()
          .answere(Answere.builder().answereId(error.getErrorCode("ok")).answereComment(error.getErrorMessage("message_0")).build())
          .validateResponse(ValidateResponse.builder().livenessScore(result).build())
          .build())
        .orElseThrow(() -> new CustomException(error.getErrorCode("ex9000"), error.getErrorMessage("message_9000")));
    } catch (final CustomException ex) {
      answere = LivenessAnswere.builder()
        .answere(Answere.builder().answereId(ex.getCode()).answereComment(ex.getInfoMessage()).build())
        .validateResponse(null)
        .build();
    } catch (final Exception ex) {
      log.error("checkLiveness Exception" + ex.getMessage());
      answere = LivenessAnswere.builder()
        .answere(Answere.builder().answereId(error.getErrorCode("ex9000")).answereComment(error.getErrorMessage("message_9000")).build())
        .validateResponse(null)
        .build();
    }
    if(personAnswere.getLivenessAnswere() != null) {
      personAnswere.getLivenessAnswere().setValidateResponse(answere.getValidateResponse());
      personAnswere.getLivenessAnswere().setAnswere(answere.getAnswere());
    } else {
      personAnswere.setLivenessAnswere(answere);
    }
    return answere;
  }

  public LivenessAnswere checkSimilarity(final ModelPersonAnswere personAnswere) {
    LivenessAnswere answere;
    try {
      final Locale locale = LocaleContextHolder.getLocale();
      final URI uri = new URI("http://liveness-service/recognize/" + locale.getLanguage() + "/similarity/");
      final HttpEntity<ValidateRequest> httpEntity = getValidateRequestHttpEntity(personAnswere);
      final ResponseEntity<Response<SimilarityResult>> responseEntity = restTemplate.exchange(
        uri,
        HttpMethod.POST,
        httpEntity,
        new ParameterizedTypeReference<Response<SimilarityResult>>() {}
      );
      if (responseEntity.getStatusCode() != HttpStatus.OK)
        throw new CustomException(error.getErrorCode("ex8887"), error.getErrorMessage("message_8887"));
      answere = Optional.ofNullable(responseEntity.getBody())
        .filter(response -> response.getCode() == 0)
        .map(Response::getResponse)
        .map(result -> LivenessAnswere
          .builder()
          .answere(Answere.builder().answereId(error.getErrorCode("ok")).answereComment(error.getErrorMessage("message_0")).build())
          .validateResponse(ValidateResponse.builder().similarityScore(result).build())
          .build())
        .orElseThrow(() -> new CustomException(error.getErrorCode("ex9000"), error.getErrorMessage("message_9000")));
    } catch (final CustomException ex) {
      answere = LivenessAnswere.builder()
        .answere(Answere.builder().answereId(ex.getCode()).answereComment(ex.getInfoMessage()).build())
        .validateResponse(null)
        .build();
    } catch (final Exception ex) {
      log.error("checkSimilarity Exception" + ex.getMessage());
      answere = LivenessAnswere.builder()
        .answere(Answere.builder().answereId(error.getErrorCode("ex9000")).answereComment(error.getErrorMessage("message_9000")).build())
        .validateResponse(null)
        .build();
    }
    if(personAnswere.getLivenessAnswere() != null) {
      personAnswere.getLivenessAnswere().setValidateResponse(answere.getValidateResponse());
      personAnswere.getLivenessAnswere().setAnswere(answere.getAnswere());
    } else {
      personAnswere.setLivenessAnswere(answere);
    }
    return answere;
  }

  private HttpEntity<ValidateRequest> getValidateRequestHttpEntity(final ModelPersonAnswere personAnswere) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    headers.setBasicAuth(username, password);
    final ValidateRequest request = getBuildValidateRequest(personAnswere);
    return new HttpEntity<>(request, headers);
  }

  private ValidateRequest getBuildValidateRequest(final ModelPersonAnswere personAnswere) {

    return ValidateRequest
      .builder()
      .additionalPhoto(personAnswere.getModelPersonPhoto().getAdditional())
      .basePhoto(personAnswere.getModelPersonPhoto().getPersonPhoto())
      // при проверки фото только на симиларити и лайвнеса не приходит сирия номер паспорта и пнфл, чтоб не было null - default value
      .serialNumber(personAnswere.getModelPersonPassport() != null ? personAnswere.getModelPersonPassport().getPersonPassport().getDocumentNumber()
        : "AA0000000")
      // при регистрации мобильного клиента не приходит серия номер девайса и пнфл, чтоб не было null - default value
      .pinpp(personAnswere.getModelPersonPassport()!= null ? personAnswere.getModelPersonPassport().getPersonPassport().getPinpp()
        : "00000000000000")
      .deviceSerialNumber(personAnswere.getModelServiceInfo() != null && personAnswere.getModelServiceInfo().getServiceInfo() != null ?
        personAnswere.getModelServiceInfo().getServiceInfo().getScannerSerial() : "NEW_RMD")
      .guid(personAnswere.getRequestGuid())
      .build();
  }

}
