package uz.asbt.digid.liveness.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import uz.asbt.digid.common.exception.ParseImageException;
import uz.asbt.digid.common.exception.RecognizeException;
import uz.asbt.digid.common.exception.RecognizeIntegrationException;
import uz.asbt.digid.common.models.dto.LivenessResult;
import uz.asbt.digid.liveness.dto.RendipLivenessResponse;
import uz.asbt.digid.liveness.dto.RendipSimilarityResponse;
import uz.asbt.digid.common.models.dto.SimilarityResult;
import uz.asbt.digid.common.models.dto.ValidateRequest;
import uz.asbt.digid.common.models.dto.ValidateResponse;
import uz.asbt.digid.liveness.utils.ImageUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RendipService {

  @Qualifier("rendipRestTemplate")
  final RestTemplate restTemplate;

  @Value("${recognize.rendip.liveness-url}")
  String urlLiveness;

  @Value("${recognize.rendip.similarity-url}")
  String urlSimilarity;

  @Async
  public CompletableFuture<SimilarityResult> similarity(final @NonNull ValidateRequest request) {
    log.info("Begin similarity");
    final CompletableFuture<RendipSimilarityResponse> similarity = similarity(request.getBasePhoto(),
      request.getAdditionalPhoto());
    return similarity.thenApply(result -> {
      log.info("Similarity Future is completed");
      return Optional.ofNullable(similarity.getNow(null))
        .map(response -> SimilarityResult.builder()
          .code(response.getResultCode())
          .similarity(response.getSimilarityScore())
          .build())
        .orElseThrow(RecognizeException::new);
    });
  }

  @Async
  public CompletableFuture<LivenessResult> liveness(final @NonNull ValidateRequest request) {
    log.info("Begin liveness");
    final CompletableFuture<RendipLivenessResponse> liveness = liveness(request.getAdditionalPhoto());
    return liveness.thenApply(result -> {
      log.info("Liveness Future is completed");
      return Optional.ofNullable(liveness.getNow(null))
        .map(response -> LivenessResult.builder()
          .code(response.getResultCode())
          .liveness(response.getLivenessScore())
          .build())
        .orElseThrow(RecognizeException::new);
    });
  }

  @Async
  public CompletableFuture<ValidateResponse> validate(final @NonNull ValidateRequest request) {
    log.info("Begin validate");
    final CompletableFuture<RendipLivenessResponse> liveness = liveness(request.getAdditionalPhoto());
    final CompletableFuture<RendipSimilarityResponse> similarity = similarity(request.getBasePhoto(),
      request.getAdditionalPhoto());
    final CompletableFuture<Void> allFuture = CompletableFuture.allOf(liveness, similarity);
    return allFuture.thenApply(result -> {
      log.info("All Futures is completed");
      final LivenessResult livenessResult =
        Optional.ofNullable(liveness.getNow(null))
          .map(response -> LivenessResult.builder()
            .code(response.getResultCode())
            .liveness(response.getLivenessScore())
            .build())
          .orElseThrow(RecognizeException::new);
      final SimilarityResult similarityResult =
        Optional.ofNullable(similarity.getNow(null))
          .map(response -> SimilarityResult.builder()
            .code(response.getResultCode())
            .similarity(response.getSimilarityScore())
            .build())
          .orElseThrow(RecognizeException::new);
      return ValidateResponse
        .builder()
        .livenessScore(livenessResult)
        .similarityScore(similarityResult)
        .build();
    });
  }

  private CompletableFuture<RendipLivenessResponse> liveness(final String photo) {
    return CompletableFuture.supplyAsync(() -> Optional.of(restTemplate
      .exchange(
        urlLiveness,
        HttpMethod.POST,
        entity(getMultipartForLiveness(photo)),
        RendipLivenessResponse.class))
      .map(HttpEntity::getBody)
      .orElseThrow(RecognizeIntegrationException::new));
  }

  private CompletableFuture<RendipSimilarityResponse> similarity(final String photo1, final String photo2) {
    return CompletableFuture.supplyAsync(() -> Optional.of(restTemplate
      .exchange(
        urlSimilarity,
        HttpMethod.POST,
        entity(getMultipartForSimilarity(photo1, photo2)),
        RendipSimilarityResponse.class))
      .map(HttpEntity::getBody)
      .orElseThrow(RecognizeIntegrationException::new));
  }

  private HttpHeaders headers() {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
    return headers;
  }

  private HttpEntity<LinkedMultiValueMap<String, Object>> entity(final LinkedMultiValueMap<String, Object> request) {
    return new HttpEntity<>(request, headers());
  }

  private LinkedMultiValueMap<String, Object> getMultipartForLiveness(final String photo) {
    return Optional.ofNullable(ImageUtils.fromBase64ToByteArray(photo))
      .map(res -> {
        final LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        final ByteArrayResource resource = new ByteArrayResource(res) {
          @Override
          public String getFilename() {
            return UUID.randomUUID().toString().concat(".jpg");
          }
        };
        map.add("input_image", resource);
        return map;
      })
      .orElseThrow(ParseImageException::new);
  }

  private LinkedMultiValueMap<String, Object> getMultipartForSimilarity(final String photo1, final String photo2) {
    return Optional.ofNullable(ImageUtils.fromBase64ToByteArray(photo1))
      .map(res -> {
        final LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        final ByteArrayResource resource = new ByteArrayResource(res) {
          @Override
          public String getFilename() {
            return UUID.randomUUID().toString().concat(".jpg");
          }
        };
        map.add("input_image_1", resource);
        return map;
      })
      .map(res -> {
        Optional.ofNullable(ImageUtils.fromBase64ToByteArray(photo2))
          .map(photo -> {
            final ByteArrayResource resource = new ByteArrayResource(photo) {
              @Override
              public String getFilename() {
                return UUID.randomUUID().toString().concat(".jpg");
              }
            };
            res.add("input_image_2", resource);
            return res;
          })
          .orElseThrow(ParseImageException::new);
        return res;
      })
      .orElseThrow(ParseImageException::new);
  }

}
