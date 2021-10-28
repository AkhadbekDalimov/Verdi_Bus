package uz.asbt.digid.liveness.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.exception.RecognizeException;
import uz.asbt.digid.common.models.dto.LivenessResult;
import uz.asbt.digid.common.models.dto.SimilarityResult;
import uz.asbt.digid.common.models.dto.ValidateRequest;
import uz.asbt.digid.common.models.dto.ValidateResponse;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.service.ErrorService;
import uz.asbt.digid.liveness.service.RendipService;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recognize")
@Slf4j
public class RecognizeController {

  private static final String SUCCESS = "message_0";
  private static final String ERROR = "message_9999";

  private final RendipService rendipService;
  private final ErrorService error;

  @PostMapping(value = "/{lang}/similarity")
  public Future<ResponseEntity<Response<SimilarityResult>>> similarity(@RequestBody @Valid final ValidateRequest request,
                                                                       @PathVariable("lang") final Locale locale) {
    return rendipService.similarity(request)
      .thenApply(response -> Optional.of(response)
        .map(r -> Response.<SimilarityResult>builder()
          .code(0)
          .message(error.getErrorMessage(SUCCESS))
          .response(r)
          .build())
        .map(ResponseEntity::ok)
        .orElseThrow(RecognizeException::new))
      .exceptionally(ex -> Optional.ofNullable(ex)
        .map(err -> {
          log.error(ex.getCause().getMessage());
          return Response.<SimilarityResult>builder()
            .code(9999)
            .message(error.getErrorMessage(ERROR))
            .build();
        })
        .map(ResponseEntity::ok)
        .orElseThrow(RecognizeException::new));
  }

  @PostMapping(value = "/{lang}/liveness")
  public Future<ResponseEntity<Response<LivenessResult>>> liveness(@RequestBody @Valid final ValidateRequest request,
                                                                   @PathVariable("lang") final Locale locale) {
    return rendipService.liveness(request)
      .thenApply(response -> Optional.of(response)
        .map(r -> Response.<LivenessResult>builder()
          .code(0)
          .message(error.getErrorMessage(SUCCESS))
          .response(r)
          .build())
        .map(ResponseEntity::ok)
        .orElseThrow(RecognizeException::new))
      .exceptionally(ex -> Optional.ofNullable(ex)
        .map(err -> {
          log.error(ex.getCause().getMessage());
          return Response.<LivenessResult>builder()
            .code(9999)
            .message(error.getErrorMessage(ERROR))
            .build();
        })
        .map(ResponseEntity::ok)
        .orElseThrow(RecognizeException::new));
  }

  @PostMapping(value = "/{lang}/validate")
  public Future<ResponseEntity<Response<ValidateResponse>>>
  check(@RequestBody @Valid final ValidateRequest request, @PathVariable("lang") final Locale locale) {
    return rendipService.validate(request)
      .thenApply(response -> Optional.of(response)
        .map(r -> Response.<ValidateResponse>builder()
          .code(0)
          .message(error.getErrorMessage(SUCCESS))
          .response(r)
          .build())
        .map(ResponseEntity::ok)
        .orElseThrow(RecognizeException::new))
      .exceptionally(ex -> Optional.ofNullable(ex)
        .map(err -> {
          log.error(ex.getCause().getMessage());
          return Response.<ValidateResponse>builder()
            .code(9999)
            .message(error.getErrorMessage(ERROR))
            .build();
        })
        .map(ResponseEntity::ok)
        .orElseThrow(RecognizeException::new));
  }

}
