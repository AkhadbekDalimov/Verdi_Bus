package uz.asbt.digid.digidservice.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.enums.PhotoCheckType;
import uz.asbt.digid.common.enums.PhotoVerificationStatus;
import uz.asbt.digid.common.enums.ValidationTypeEnum;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.dto.ValidateResponse;
import uz.asbt.digid.common.models.dto.ValidationType;
import uz.asbt.digid.common.models.entity.client.LivenessAnswere;
import uz.asbt.digid.common.models.entity.client.ModelPerson;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.entity.client.ModelPersonPassport;
import uz.asbt.digid.common.models.entity.client.ModelPersonPassportAnswere;
import uz.asbt.digid.common.models.entity.client.ModelPersonPhoto;
import uz.asbt.digid.common.models.entity.client.ServiceInfo;
import uz.asbt.digid.common.service.ErrorService;
import uz.asbt.digid.digidservice.service.ICrmService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;

import static uz.asbt.digid.common.constants.Const.LOCAL_DATE_JSON_FORMATTER;

@Component
@Slf4j
@RequiredArgsConstructor
public class FieldValidator {

  private final ErrorService error;

  private final ICrmService crmService;
  @Value("${rendip-scores.liveness}")
  private double livenessScore;
  @Value("${rendip-scores.similarity}")
  private double similarityScore;

  public boolean isLiveness(final ModelPersonAnswere personAnswere) {
    return Optional.of(personAnswere)
      .flatMap(res -> Optional.ofNullable(res.getModelServiceInfo())
        .flatMap(obj -> Optional.ofNullable(obj.getServiceInfo())
          .map(ServiceInfo::getScannerSerial)
          .map(sn -> crmService.findBySerialNumber(sn, Locale.getDefault()))
          .map(ClientDTO::getDevice)))
      .filter(this::checkPhotoValidationAccess)
      .flatMap(res -> Optional.ofNullable(personAnswere.getModelPersonPhoto())
        .map(photo -> {
          final String additionalResult = Optional.ofNullable(photo.getAdditional()).orElse(null);
          return additionalResult != null;
        }))
      .orElse(false);
  }

  private boolean checkPhotoValidationAccess(final DeviceDTO res) {
    return res.getLivenessCheck() == ValidationTypeEnum.CHECK.getType() || res.getSimilarityCheck() == ValidationTypeEnum.CHECK.getType();
  }

  public boolean validateMobileRequest(final ModelPersonAnswere personAnswere) {
    final boolean isValid;
    final ModelPersonPassportAnswere passportAnswere = personAnswere.getModelPersonPassport();
    if (passportAnswere != null && personAnswere.getClientPubKey() != null && !personAnswere.getClientPubKey().isEmpty()) {
      final ModelPersonPassport personPassport = passportAnswere.getPersonPassport();
      if (personPassport != null) {
        isValid = isValid(personPassport);
//        if (!checkExpiryDate(personPassport.getExpiryDate())){
//          isValid = false;
//        }
      } else {
        isValid = false;
      }
    } else {
      isValid = false;
    }
    return isValid;
  }

  public boolean validate(final ModelPersonAnswere personAnswere) {
    final boolean isValid;
    final ModelPersonPassportAnswere passportAnswere = personAnswere.getModelPersonPassport();
    if (passportAnswere != null) {
      final ModelPersonPassport personPassport = passportAnswere.getPersonPassport();
      if (personPassport != null) {
        isValid = isValid(personPassport);
//        if (personPassport.getPinpp() != null && personPassport.getPinpp().length() != 14) {
//          isValid = false;
//        }
      } else {
        isValid = false;
      }
    } else {
      isValid = false;
    }
    return isValid;
  }

  private boolean isValid(final ModelPersonPassport personPassport) {
    boolean isValid = true;
//    if (personPassport.getPinpp() == null || personPassport.getDocumentNumber().equals("")) {
//      isValid = false;
//    }
    if (personPassport.getDocumentNumber() == null || personPassport.getDocumentNumber().equals("")) {
      isValid = false;
    }
    if (personPassport.getDocumentType() == null || personPassport.getDocumentType().equals("")) {
      isValid = false;
    }
    return isValid;
  }

  public boolean checkExpiryDate(final String expiryDate) {
    if (expiryDate == null) {
      return false;
    }
    final LocalDate date = LocalDate.parse(expiryDate, LOCAL_DATE_JSON_FORMATTER);
    final LocalDate today = LocalDate.now(ZoneId.systemDefault());
    return today.isBefore(date);
  }

  public boolean validateModelPhoto(final ModelPersonAnswere personAnswere) {
    return Optional.of(personAnswere)
      .flatMap(res -> Optional.ofNullable(personAnswere.getModelPersonPhoto())
        .map(photo -> {
          final String baseResult = Optional.ofNullable(photo.getPersonPhoto()).orElse(null);
          final String additionalResult = Optional.ofNullable(photo.getAdditional()).orElse(null);
          return baseResult != null && additionalResult != null;
        }))
      .orElse(false);
  }

  public int validatePersonPhotos(final LivenessAnswere livenessAnswere) {
    log.info("rendip scores validation {}", livenessAnswere);

    int isValid = PhotoVerificationStatus.PHOTOS_INVALID.getStatus();

    if (livenessAnswere != null && livenessAnswere.getValidationType() != null) {
      final ValidateResponse validateResponse = livenessAnswere.getValidateResponse();
      final ValidationType validationType = livenessAnswere.getValidationType();
      if (validationType.getIsLivenessCheck() == PhotoCheckType.CHECK.getType()
        && validationType.getIsSimilarityCheck() == PhotoCheckType.CHECK.getType()) {
        isValid = Optional.ofNullable(validateResponse)
          .map(photo -> {
            final Float liveness = Optional.ofNullable(validateResponse.getLivenessScore()
              .getLiveness()).orElse(null);
            final Float similarity = Optional.ofNullable(validateResponse.getSimilarityScore()
              .getSimilarity()).orElse(null);
            log.info("livenes check: " + livenessScore + " - " + liveness + " | " + similarityScore + " - " + similarity);
            if (liveness != null && liveness >= livenessScore && similarity != null && similarity >= similarityScore) {
              log.info("isValid {}", PhotoVerificationStatus.PHOTOS_VALID.getStatus());
              return PhotoVerificationStatus.PHOTOS_VALID.getStatus();
            } else if (liveness != null && liveness < livenessScore) {
              log.info("isValid {}", PhotoVerificationStatus.PHOTO_LIVENESS_NOT_VALID.getStatus());
              return PhotoVerificationStatus.PHOTO_LIVENESS_NOT_VALID.getStatus();
            } else {
              log.info("isValid {}", PhotoVerificationStatus.PHOTO_SIMILARITY_NOT_VALID.getStatus());
              return PhotoVerificationStatus.PHOTO_SIMILARITY_NOT_VALID.getStatus();
            }

          })
          .orElse(PhotoVerificationStatus.PHOTOS_INVALID.getStatus());
      } else {
        if (validationType.getIsLivenessCheck() == PhotoCheckType.CHECK.getType()) {
          isValid = rendipScoreValidation(validateResponse, validateResponse.getLivenessScore().getLiveness(),
            livenessScore, PhotoVerificationStatus.PHOTO_LIVENESS_NOT_VALID);
          log.info("isValid {}", PhotoVerificationStatus.PHOTO_LIVENESS_NOT_VALID.getStatus());
        }
        if (validationType.getIsSimilarityCheck() == PhotoCheckType.CHECK.getType()) {
          isValid = rendipScoreValidation(validateResponse, validateResponse.getSimilarityScore().getSimilarity(),
            similarityScore, PhotoVerificationStatus.PHOTO_SIMILARITY_NOT_VALID);
          log.info("isValid {}", PhotoVerificationStatus.PHOTO_SIMILARITY_NOT_VALID.getStatus());
        }
      }
    }
    log.info("is valid last {}", isValid);
    return isValid;
  }

  private Integer rendipScoreValidation(final ValidateResponse validateResponse, final Float rendipResponseScore,
                                        final double minValidScore, final PhotoVerificationStatus photoNotValidStatus) {
    return Optional.ofNullable(validateResponse)
      .map(photo -> {
        if (rendipResponseScore != null && minValidScore <= rendipResponseScore) {
          return PhotoVerificationStatus.PHOTOS_VALID.getStatus();
        } else {
          return photoNotValidStatus.getStatus();
        }
      })
      .orElse(PhotoVerificationStatus.PHOTOS_INVALID.getStatus());
  }

  public boolean validatePassportRequestDataAndNibddResponseData(final ModelPersonAnswere personAnswere) {
    log.info("validation {}", personAnswere);
    boolean isValid = true;
    final ModelPersonPassport personPassport = personAnswere.getModelPersonPassport().getPersonPassport();
    final ModelPerson nibddResponse = personAnswere.getPerson();
    if (nibddResponse != null && (nibddResponse.getDocumentSerialNumber() == null
      || !nibddResponse.getDocumentSerialNumber().equals(personPassport.getDocumentNumber()))) {
      isValid = false;
    }
    if (nibddResponse != null && (nibddResponse.getDocumentDateValid() == null || !checkExpiryDate(nibddResponse.getDocumentDateValid()))) {
      isValid = false;
    }
    return isValid;
  }

  public static boolean isPhotoFromNibddNeed(final ModelPersonAnswere person) {
    if (person.getModelPersonPhoto() == null) {
      person.setModelPersonPhoto(new ModelPersonPhoto());
      return true;
    }
    if (person.getModelPersonPhoto().getPersonPhoto() == null
      || person.getModelPersonPhoto().getPersonPhoto().isEmpty()) {
      return true;
    }
    return false;
  }

}
