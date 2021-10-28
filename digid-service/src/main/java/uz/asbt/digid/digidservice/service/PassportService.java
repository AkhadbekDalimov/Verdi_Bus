package uz.asbt.digid.digidservice.service;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.enums.LoginType;
import uz.asbt.digid.common.enums.PhotoCheckType;
import uz.asbt.digid.common.enums.PhotoVerificationStatus;
import uz.asbt.digid.common.enums.ValidationTypeEnum;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.CrmMobileRegRequest;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.dto.RequestMonitorDTO;
import uz.asbt.digid.common.models.dto.ValidateResponse;
import uz.asbt.digid.common.models.dto.ValidationType;
import uz.asbt.digid.common.models.entity.client.Answere;
import uz.asbt.digid.common.models.entity.client.LivenessAnswere;
import uz.asbt.digid.common.models.entity.client.ModelAddressAnswere;
import uz.asbt.digid.common.models.entity.client.ModelMRZAnswere;
import uz.asbt.digid.common.models.entity.client.ModelPerson;
import uz.asbt.digid.common.models.entity.client.ModelPersonAdditional;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.entity.client.ModelPersonPassport;
import uz.asbt.digid.common.models.entity.client.ModelPersonPassportAnswere;
import uz.asbt.digid.common.models.rest.PhysicalPhotoResponse;
import uz.asbt.digid.common.models.rest.PhysicalResponse;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.service.ErrorService;
import uz.asbt.digid.common.service.monitor.IReport;
import uz.asbt.digid.digidservice.builder.ModelAddressBuilder;
import uz.asbt.digid.digidservice.builder.ModelPersonBuilder;
import uz.asbt.digid.digidservice.builder.ModelTempAddressBuilder;
import uz.asbt.digid.digidservice.model.dto.PassportDataDTO;
import uz.asbt.digid.digidservice.service.impl.CrmService;
import uz.asbt.digid.digidservice.service.impl.NibbdPhysicalPhoto;
import uz.asbt.digid.digidservice.validator.FieldValidator;
import uz.asbt.digid.digidservice.validator.ValidationUtil;

import java.util.Locale;
import java.util.Optional;

import static uz.asbt.digid.digidservice.validator.FieldValidator.isPhotoFromNibddNeed;

@Service
@Slf4j
public class PassportService {

  private static final String ERROR_MSG = "Nibbd error: {}";

  private final FieldValidator validator;
  private final IntegrationService<ModelPersonAnswere, PhysicalResponse> integrationService;
  private final IntegrationService<ModelPersonAnswere, PhysicalPhotoResponse> nibbdPhysicalPhoto;
  private final ErrorService error;
  private final ModelPersonBuilder personBuilder;
  private final ModelAddressBuilder addressBuilder;
  private final ModelTempAddressBuilder tempAddressBuilder;
  private final LivenessService livenessService;
  private final SaveAllDataService saveAllDataService;
  private final PhotoDataService photoDataService;
  private final CrmService crmService;
  private final IReport monitorService;
  private final PassportDataService passportDataService;

  public PassportService(final FieldValidator validator,
                         @Qualifier("nibbdIntegration") final IntegrationService<ModelPersonAnswere, PhysicalResponse> integrationService,
                         final ErrorService error,
                         final ModelPersonBuilder personBuilder,
                         final ModelAddressBuilder addressBuilder,
                         final LivenessService livenessService,
                         final ModelTempAddressBuilder tempAddressBuilder,
                         @Qualifier("nibbdPhysicalPhoto") final NibbdPhysicalPhoto nibbdPhysicalPhoto,
                         final SaveAllDataService saveAllDataService,
                         final PhotoDataService photoDataService,
                         final CrmService crmService,
                         @Qualifier("monitorService") final IReport monitorService,
                         final PassportDataService passportDataService) {
    this.validator = validator;
    this.integrationService = integrationService;
    this.error = error;
    this.personBuilder = personBuilder;
    this.addressBuilder = addressBuilder;
    this.tempAddressBuilder = tempAddressBuilder;
    this.livenessService = livenessService;
    this.nibbdPhysicalPhoto = nibbdPhysicalPhoto;
    this.saveAllDataService = saveAllDataService;
    this.photoDataService = photoDataService;
    this.crmService = crmService;
    this.monitorService = monitorService;
    this.passportDataService = passportDataService;
  }

  @SneakyThrows
  public ResponseEntity<Response<ModelPersonAnswere>> getPassportInfo(final ModelPersonAnswere person,
                                                                      final Locale locale) {
    log.info("Validate incoming request by GUID: {}", person);
    //Проверка наличия обязательных параметров для поиска
    if (!validator.validate(person)) {
      throw new CustomException(error.getErrorCode("ex108"));
    }

    final ModelMRZAnswere mrzAnswere = person.getModelMRZ();
    log.info("ModelMRZAnswere is {}", mrzAnswere);

    if (isPhotoFromNibddNeed(person)) {
      try {
        getPersonPhotoFromNibbd(person);
      } catch (Exception ex) {
        log.error("Error getting photo:" + ex.getMessage());
      }
    }


    log.info("begin checking liveness");
    if (validator.isLiveness(person)) {
      log.info("begin checking photo {}", person.getLivenessAnswere().getValidationType());
      final LivenessAnswere livenessAnswere = getRendipAnswere(person);
      log.info("End rendip validation {}", livenessAnswere);
//      person.getLivenessAnswere().setValidateResponse(livenessAnswere.getValidateResponse());
      person.getLivenessAnswere().setValidateResponse(checkPhotoValidation(person, livenessAnswere));
    }

    log.info("begin getting passport data");
    final ResponseEntity<Response<ModelPersonAnswere>> returnPerson = createResponseEntityPersonModelAnswere(person, locale, mrzAnswere);
    validatePersonDataByPinppAndBirthDate(returnPerson, person, locale);
    if (!validator.validatePassportRequestDataAndNibddResponseData(returnPerson.getBody().getResponse())) {
      log.info("person.getLivenessAnswere() {}", returnPerson.getBody().getResponse());
      throwValidationError(returnPerson.getBody().getResponse());
    }


    makePhotoEmptyForResponse(returnPerson, person);
    if (returnPerson.getBody().getCode() == 0) {
      log.info("Begin saving all data");
      saveAllDataService.saveAllDataToDB(returnPerson.getBody().getResponse(), locale);
    }
    return returnPerson;
  }

  @SneakyThrows
  public ResponseEntity<Response<ModelPersonAnswere>> getPassportInfoForMobile(final ModelPersonAnswere person,
                                                                               final Locale locale, final int loginType) {
    //Проверка наличия обязательных параметров для регистрации нового клиента
    if (LoginType.REGISTRATION.getType() == loginType && !validator.validateMobileRequest(person)) {
      throw new CustomException(error.getErrorCode("ex108"));
    }

    //Проверка наличия обязательных параметров для поиска клиента
    if (LoginType.VERIFICATION.getType() == loginType) {
      final ClientDTO clientDTO = crmService.findBySerialNumber(person.getModelServiceInfo().getServiceInfo().getScannerSerial(), locale);
      log.info("client from db {}", clientDTO);
      person.setModelPersonPassport(getPersonPassportDataFromDB(clientDTO.getDevice().getOrganization().getPinpp()));
    }

    final ModelMRZAnswere mrzAnswere = person.getModelMRZ();
    log.info("ModelMRZAnswere is {}", mrzAnswere);

    if (isPhotoFromNibddNeed(person)) {
      log.info("Started getting photo");
      try {
        getPersonPhotoFromNibbd(person);
      } catch (Exception ex) {
        log.error("Error getting photo:" + ex.getMessage());
      }
      log.info("person photo model {} ", person.getModelPersonPhoto().getPersonPhoto());
    }

    photoValidationRequest(person);

    final ResponseEntity<Response<ModelPersonAnswere>> returnPerson = createResponseEntityPersonModelAnswere(person, locale, mrzAnswere);

    if (returnPerson.getBody() != null && returnPerson.getBody().getResponse() != null) {
      person.getModelPersonPassport().getPersonPassport().setBirthDate(returnPerson.getBody().getResponse().getPerson().getBirthDate());
    }

    if (!validator.validatePassportRequestDataAndNibddResponseData(person)) {
      throwValidationError(returnPerson.getBody().getResponse());
    }
    log.info("person result nibdd: {}", returnPerson);

    final ClientDTO clientDTO = getClientData(person, locale, loginType, returnPerson);
    returnPerson.getBody().getResponse().setClient(buildClientForMobileResponse(clientDTO));

    if ((returnPerson.getBody()) != null && returnPerson.getBody().getResponse() != null) {
      saveAllDataService.saveAllDataToDB(returnPerson.getBody().getResponse(), locale);
    }
    return returnPerson;

  }

  private void photoValidationRequest(final ModelPersonAnswere person) {
    log.info("Started photo validation");
    if (validator.validateModelPhoto(person)) {
      setValidationTypeForMobileClient(person);
      final LivenessAnswere livenessAnswere = getRendipAnswere(person);
      log.info("End rendip validation {}", person.getLivenessAnswere());
      final int validationStatus = validator.validatePersonPhotos(person.getLivenessAnswere());
      if (validationStatus != PhotoVerificationStatus.PHOTOS_VALID.getStatus()) {
        throwValidationErrorPhoto(person, validationStatus);
//        getValidateErrorResponseResponseEntity(person);
      } else {
        person.getLivenessAnswere().setValidateResponse(checkPhotoValidation(person, livenessAnswere));
      }
    }
  }

  private ClientDTO getClientData(final ModelPersonAnswere person, final Locale locale,final int loginType,final ResponseEntity<Response<ModelPersonAnswere>> returnPerson) {
    final ClientDTO clientDTO;
    log.info("Begin sending mobile data to CRM:");
    if (LoginType.REGISTRATION.getType() == loginType) {
      final CrmMobileRegRequest mobileRegRequest = buildCrmMobileRegistrationRequest(returnPerson.getBody().getResponse());
      clientDTO = crmService.sendMobileClientDataToCrm(mobileRegRequest, locale);
    } else {
      clientDTO = crmService.findBySerialNumber(person.getModelServiceInfo().getServiceInfo().getScannerSerial(), locale);
    }
    return clientDTO;
  }

  private void setValidationTypeForMobileClient(final ModelPersonAnswere person) {
    log.info("set validation type for mobile, must check similarity and liveness");
    person.setLivenessAnswere(LivenessAnswere.builder()
      .validationType(ValidationType.builder()
        .isLivenessCheck(ValidationTypeEnum.CHECK.getType())
        .isSimilarityCheck(ValidationTypeEnum.CHECK.getType())
        .build())
      .build());
  }

  private ValidateResponse checkPhotoValidation(final ModelPersonAnswere person, final LivenessAnswere livenessAnswere) {
    log.info("begin checking photo");
    if (livenessAnswere.getAnswere().getAnswereId() == error.getErrorCode("ok")) {
      final int photoVerificationStatus = validator.validatePersonPhotos(person.getLivenessAnswere());
      if (photoVerificationStatus != PhotoVerificationStatus.PHOTOS_VALID.getStatus()) {
        person.getLivenessAnswere().setValidateResponse(livenessAnswere.getValidateResponse());
        throwValidationErrorPhoto(person, photoVerificationStatus);
      }
    }
    return livenessAnswere.getValidateResponse();
  }

  @SneakyThrows
  private ResponseEntity<Response<ClientDTO>> buildResponseResponseEntityForMobileClient(final ClientDTO clientDTO) {
    return Optional.ofNullable(ClientDTO.builder().device(DeviceDTO.builder()
      .serialNumber(clientDTO.getDevice().getSerialNumber())
      .build()).build())
      .map(c -> ResponseEntity.ok(Response
        .<ClientDTO>builder()
        .code(error.getErrorCode("ok"))
        .message(error.getErrorMessage("message_0"))
        .response(c)
        .build()))
      .orElseThrow(() -> {
        throw new CustomException(error.getErrorCode("ex106"));
      });
  }

  private ClientDTO buildClientForMobileResponse(final ClientDTO clientDTO) {
    log.info("build client for mobile response {}", clientDTO);
    return Optional.ofNullable(ClientDTO.builder().device(DeviceDTO.builder()
      .serialNumber(
        clientDTO
          .getDevice()
          .getSerialNumber())
      .build()).build())
      .orElse(null);
  }

  private ModelPersonPassportAnswere getPersonPassportDataFromDB(final String pinpp) {
    log.info("getting passport data from db by pinpp - {}", pinpp);
    return Optional.ofNullable(pinpp)
      .map(p -> {
        final RequestMonitorDTO requestMonitorDTO = monitorService.findLastRequestMonitorByPinpp(p);
        final Optional<PassportDataDTO> passportData = passportDataService.findPassportBySerialNumber(requestMonitorDTO.getDocumentNumber());
        if(passportData.isPresent()) {
          log.info("passport data {},", passportData);
        } else {
          log.info("passport data is null");
          throw new EntityFindException();
        }
        return passportData.get();
      })
      .map(res -> {
        final ModelPersonPassportAnswere modelPersonPassportAnswere = new ModelPersonPassportAnswere();
        final ModelPersonPassport modelPersonPassport = new ModelPersonPassport();
        modelPersonPassport.setPinpp(pinpp);
        modelPersonPassport.setDocumentNumber(res.getDocumentSerialNumber());
        modelPersonPassport.setDocumentType(res.getDocumentType());
        modelPersonPassport.setBirthDate(res.getBirthDate());
        modelPersonPassportAnswere.setPersonPassport(modelPersonPassport);
        return modelPersonPassportAnswere;
      })
      .orElseThrow(EntityFindException::new);
  }

  private LivenessAnswere getRendipAnswere(final ModelPersonAnswere person) {
    log.info("check liveness {}", person.getLivenessAnswere());
    if (person.getLivenessAnswere() != null && person.getLivenessAnswere().getValidationType() != null) {
      if (person.getLivenessAnswere().getValidationType().getIsLivenessCheck() == PhotoCheckType.CHECK.getType()
        && person.getLivenessAnswere().getValidationType().getIsSimilarityCheck() == PhotoCheckType.CHECK.getType()) {
        log.info("validate photos");
        return livenessService.livenessResult(person);
      } else {
        if (person.getLivenessAnswere().getValidationType().getIsLivenessCheck() == PhotoCheckType.CHECK.getType()) {
          log.info("photo's liveness");
          return livenessService.checkLiveness(person);
        }
        if (person.getLivenessAnswere().getValidationType().getIsSimilarityCheck() == PhotoCheckType.CHECK.getType()) {
          log.info("photos' similairy");
          return livenessService.checkSimilarity(person);
        }
      }
    }
    return new LivenessAnswere();
  }

  @SneakyThrows
  private ResponseEntity<Response<ModelPersonAnswere>> createResponseEntityPersonModelAnswere(final ModelPersonAnswere person, final Locale locale, final ModelMRZAnswere mrzAnswere) {
    return Optional.ofNullable(integrationService.get(person))
      .map(response -> {
        checkResponseStatus(response);
        log.info("Begin parse NIBBD info");
        final ModelPerson modelPerson = personBuilder.build(
          response.getResponse(),
          person.getModelPersonPassport().getPersonPassport(),
          mrzAnswere == null ? null : person.getModelMRZ().getMrz(),
          locale
        );
        log.info("Set INN info begin");
        if (person.getAdditional() != null) {
          person.getAdditional().setInn(response.getResponse().getInn());
          person.getAdditional().setInnDate(response.getResponse().getInnRegistrated());
          person.getAdditional().setTaxCode(response.getResponse().getInnRegistratedGni());
        } else {
          final ModelPersonAdditional additional = new ModelPersonAdditional();
          additional.setInn(response.getResponse().getInn());
          additional.setInnDate(response.getResponse().getInnRegistrated());
          additional.setTaxCode(response.getResponse().getInnRegistratedGni());
          person.setAdditional(additional);
        }
        log.info("Set INN info end");
        log.info("End parse NIBBD info");
        //Address validation. If address present set address into model else set error message
        if (ValidationUtil.isAddressPresent(response.getResponse())) {
          log.info("Begin parse NIBBD address info");
          person.setAddress(addressBuilder.build(response.getResponse(), locale));
          log.info("End parse NIBBD address info");
        } else {
          final ModelAddressAnswere addressAnswere = new ModelAddressAnswere();
          final Answere addrAns = new Answere();
          addrAns.setAnswereId(error.getErrorCode("ex110"));
          addrAns.setAnswereComment(error.getErrorMessage("message_110"));
          addressAnswere.setAnswere(addrAns);
          person.setAddress(addressAnswere);
        }
        if (ValidationUtil.isTemporaryAddressPresent(response.getResponse())) {
          log.info("Begin parse NIBBD temp address info");
          person.setAddressTemporary(tempAddressBuilder.build(response.getResponse(), locale));
          log.info("End parse NIBBD temp address info");
        }
        person.setPerson(modelPerson);
        person.setAnswere(Answere
          .builder()
          .answereId(error.getErrorCode("ok"))
          .answereComment(error.getErrorMessage("message_0"))
          .build());
        return person;
      })
      .map(p -> ResponseEntity.ok(Response
        .<ModelPersonAnswere>builder()
        .code(error.getErrorCode("ok"))
        .message(error.getErrorMessage("message_0"))
        .response(p)
        .build()))
      .orElseThrow(() -> {
        throw new CustomException(error.getErrorCode("ex106"));
      });
  }

  private PhysicalPhotoResponse getPersonPhotoFromNibbd(final ModelPersonAnswere person) {
    log.info("Begin getting physical photo from nibbd");
    final PhysicalPhotoResponse physicalResponse = nibbdPhysicalPhoto.get(person);
    person.getModelPersonPhoto().setPersonPhoto(physicalResponse.getResponse().getPhoto());
    person.getModelPersonPassport().getPersonPassport().setPinpp(physicalResponse.getResponse().getPin());
    if (physicalResponse.getResponse().getPhoto() != null && !physicalResponse.getResponse().getPhoto().isEmpty()) {
      person.getModelPersonPhoto().setAnswere(Answere
        .builder()
        .answereId(error.getErrorCode("ex111"))
        .answereComment(error.getErrorMessage("message_111"))
        .build());
    }
    return physicalResponse;
  }


  private void checkResponseStatus(@NonNull final PhysicalResponse physicalResponse) {
    log.info("check response status {}", physicalResponse);
    if (!physicalResponse.getResult().getCode().equals("02000")) {
      if (physicalResponse.getResult().getCode().equals("02209")
        || physicalResponse.getResult().getCode().equals("02211")) {
        log.error(ERROR_MSG, physicalResponse.getResult().getMessage());
        throw new CustomException(error.getErrorCode("ex108"), physicalResponse.getResult().getMessage());
      } else if (physicalResponse.getResult().getCode().equals("02374")
        || physicalResponse.getResult().getCode().equals("02379")) {
        log.error(ERROR_MSG, physicalResponse.getResult().getMessage());
        throw new CustomException(error.getErrorCode("ex105"), physicalResponse.getResult().getMessage());
      } else {
        //02361 - Запрос отклонён из-за отсутствия связи с системой ГЦП в межведомственной платформе
        //02365 - Ошибка, истекло время ожидания ответа из ГНК в межведомственной платформе
        log.error(ERROR_MSG, physicalResponse.getResult().getMessage());
        throw new CustomException(error.getErrorCode("ex106"), physicalResponse.getResult().getMessage());
      }
    }
  }

  private CrmMobileRegRequest buildCrmMobileRegistrationRequest(final ModelPersonAnswere person) {
    log.info("build crm mobile registration request");
    return CrmMobileRegRequest.builder()
      .mobileData(person.getModelMobileData())
      .modelAddress(person.getAddress().getModelAddress())
      .pinpp(person.getPerson().getPinpp())
      .mobileData(person.getModelMobileData())
      .clientPubKey(person.getClientPubKey())
      .requestGuid(person.getRequestGuid())
      .name(person.getPerson().getNameL())
      .surname(person.getPerson().getSurnameL())
      .patronym(person.getPerson().getPatronymL())
      .appId(person.getAppId())
      .build();
  }

  private void validatePersonDataByPinppAndBirthDate(final ResponseEntity<Response<ModelPersonAnswere>> returnPerson,
                                                     final ModelPersonAnswere person,
                                                     final Locale locale) {
    if (returnPerson.getBody() != null && returnPerson.getBody().getResponse() != null) {
      if (person.getModelPersonPassport().getPersonPassport().getPinpp() == null || person.getModelPersonPassport().getPersonPassport().getPinpp().isEmpty()) {
        person.getModelPersonPassport().getPersonPassport().setPinpp(
          returnPerson
            .getBody()
            .getResponse()
            .getModelPersonPassport()
            .getPersonPassport()
            .getPinpp());
        final ResponseEntity<Response<ModelPersonAnswere>> personDataByPinpp =
          createResponseEntityPersonModelAnswere(person,
            locale,
            person.getModelMRZ());
        if (!person.getPerson().getDocumentSerialNumber().equals(returnPerson.getBody().getResponse().getPerson().getDocumentDateValid())
          && !person.getPerson().getBirthDate().equals(returnPerson.getBody().getResponse().getPerson().getBirthDate())) {
          throwValidationError(returnPerson.getBody().getResponse());
        }
      }
    }
  }

  private void makePhotoEmptyForResponse(final ResponseEntity<Response<ModelPersonAnswere>> returnPerson, final ModelPersonAnswere person) {
    log.info("person photo ");
    try {
      if (returnPerson.getBody().getResponse().getModelPersonPhoto().getAnswere().getAnswereId() != error.getErrorCode("ex111")) {
        returnPerson.getBody().getResponse().getModelPersonPhoto().setPersonPhoto("");
      } else {
        returnPerson.getBody().getResponse().getModelPersonPhoto().setPersonPhoto(person.getModelPersonPhoto().getPersonPhoto());
      }
      returnPerson.getBody().getResponse().getModelPersonPhoto().setAdditional("");
    } catch (final NullPointerException ex) {
      log.info(ex.getMessage());
    }
  }

  private ResponseEntity<Response<ModelPersonAnswere>> throwValidationError(final ModelPersonAnswere person) {
//    person.setAddress(new ModelAddressAnswere());
//    person.setAddress(new ModelAddressAnswere());
//    person.setPerson(new ModelPerson());
//    person.setAdditional(new ModelPersonAdditional());
    throw new CustomException(error.getErrorCode("ex108"), error.getErrorMessage("message_108"));
  }

  private void throwValidationErrorPhoto(final ModelPersonAnswere person, final int photoVerificationStatus) {
    log.info("validation error status {}", photoVerificationStatus);
//    person.setAddress(new ModelAddressAnswere());
//    person.setPerson(new ModelPerson());
//    person.setAdditional(new ModelPersonAdditional());
    throw new CustomException(error.getErrorCode("ex" + photoVerificationStatus), error.getErrorMessage("message_" + photoVerificationStatus));
  }
}
