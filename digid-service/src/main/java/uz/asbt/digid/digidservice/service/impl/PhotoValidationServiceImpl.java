package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.enums.PhotoCheckType;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.models.entity.client.LivenessAnswere;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.PhysicalPhotoResponse;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.service.ErrorService;
import uz.asbt.digid.digidservice.service.LivenessService;
import uz.asbt.digid.digidservice.service.PhotoValidationService;
import uz.asbt.digid.digidservice.validator.FieldValidator;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhotoValidationServiceImpl implements PhotoValidationService {

  private final ErrorService error;
  private final FieldValidator validator;
  private final LivenessService livenessService;
  @Qualifier("nibbdPhysicalPhoto")
  private final NibbdPhysicalPhoto nibbdPhysicalPhoto;

  @Override
  public ResponseEntity<Response<LivenessAnswere>> checkLiveness(final ModelPersonAnswere person) {
//    photoValidation(person);

    return responseEntityBuild(livenessService.checkLiveness(person), person);
  }

  @Override
  public ResponseEntity<Response<LivenessAnswere>> checkSimilarity(final ModelPersonAnswere person) {
//    photoValidation(person);

//    getNibddPhoto(person);

    return responseEntityBuild(livenessService.checkSimilarity(person), person);
  }

  @Override
  public ResponseEntity<Response<LivenessAnswere>> validate(final ModelPersonAnswere person) {
          return  responseEntityBuild(livenessService.livenessResult(person), person);
  }

  private LivenessAnswere getRendipAnswere(final ModelPersonAnswere person) {
    log.info("check liveness {}", person.getLivenessAnswere());
    if (person.getLivenessAnswere() != null && person.getLivenessAnswere().getValidationType() != null) {
      if (person.getLivenessAnswere().getValidationType().getIsLivenessCheck() == PhotoCheckType.CHECK.getType()
        && person.getLivenessAnswere().getValidationType().getIsSimilarityCheck() == PhotoCheckType.CHECK.getType()) {
        return livenessService.livenessResult(person);
      } else {
        if (person.getLivenessAnswere().getValidationType().getIsLivenessCheck() == PhotoCheckType.CHECK.getType()) {
          return livenessService.checkLiveness(person);
        }
        if (person.getLivenessAnswere().getValidationType().getIsSimilarityCheck() == PhotoCheckType.CHECK.getType()) {
          return livenessService.checkSimilarity(person);
        }
      }
    }
    return null;
  }

  private void photoValidation(final ModelPersonAnswere person) {
    if (!validator.validate(person)) {
      throw new CustomException(error.getErrorCode("ex108"));
    }
  }

  private void getNibddPhoto(final ModelPersonAnswere person) {
    if (person.getModelPersonPhoto().getPersonPhoto() == null || person.getModelPersonPhoto().getPersonPhoto().isEmpty()) {
      final PhysicalPhotoResponse physicalResponse = nibbdPhysicalPhoto.get(person);
      person.getModelPersonPhoto().setPersonPhoto(physicalResponse.getResponse().getPhoto());
    }
  }

  @SneakyThrows
  private ResponseEntity<Response<LivenessAnswere>> responseEntityBuild(final LivenessAnswere livenessAnswere, final ModelPersonAnswere person) {
    livenessAnswere.setValidationType(person.getLivenessAnswere().getValidationType());
    return Optional.ofNullable(livenessAnswere)
      .map(response -> ResponseEntity.ok(Response
        .<LivenessAnswere>builder()
        .code(error.getErrorCode("ok"))
        .message(error.getErrorMessage("message_0"))
        .response(response)
        .build()))
      .orElseThrow(() -> {
        throw new CustomException(error.getErrorCode("ex108"));
      });
  }
}
