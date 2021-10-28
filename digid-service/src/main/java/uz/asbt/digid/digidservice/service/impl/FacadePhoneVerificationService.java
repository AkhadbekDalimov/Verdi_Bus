package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.enums.PhoneVerificationStatus;
import uz.asbt.digid.digidservice.model.entity.PhoneVerification;
import uz.asbt.digid.digidservice.service.IFacadePhoneVerificationService;
import uz.asbt.digid.digidservice.service.PhoneVerificationService;
import uz.asbt.digid.digidservice.service.SmsSendingService;

import static uz.asbt.digid.digidservice.util.SmsUtils.generateCodeForPhone;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacadePhoneVerificationService implements IFacadePhoneVerificationService {

  @Value("${sms.max-count}")
  private Integer maxSmsCodeRequest;
  private final PhoneVerificationService verificationService;
  private final SmsSendingService smsSendingService;
  private static final int SMS_STATUS_CHECKED = 1;
  private static final int SMS_STATUS_UNCHECKED = 0;

  @Override
  public int sendSmsToPhone(final String phoneNumber) {
    final Integer smsCode = generateCodeForPhone();
    log.info("facade service code for phone {} {}", smsCode, phoneNumber);
    if (!verificationService.isPhoneNumberExistInDBWithCurrentDate(phoneNumber)) {
      log.info("begin saving request for {}", phoneNumber);
      verificationService.saveSmsCodeAndPhoneNumber(phoneNumber, smsCode);
    } else {
      log.info("re-request for phone {}", phoneNumber);
      final PhoneVerification phoneVerification = verificationService.getPhoneVerificationByPhoneAndCurrentDate(phoneNumber);
      if (maxSmsCodeRequest < phoneVerification.getCount()) {
        log.info("reached max request count {} {}", phoneNumber, maxSmsCodeRequest);
//        smsSendingService.informPersonAbtReachingMaxSmsRequest(phoneNumber);
        return PhoneVerificationStatus.EXCEEDED_MAX_COUNT.getStatus();
      }

      updatePhoneVerificationRequest(phoneNumber, smsCode, phoneVerification);
    }

    log.info("begin sending sms code {}", phoneNumber);
    smsSendingService.sendSmsCodeToPhoneNumber(phoneNumber, smsCode);
    return PhoneVerificationStatus.SMS_CODE_SEND.getStatus();
  }

  @Override
  public int checkSmsCodeAndPhone(final String phoneNumber, final Integer smsCodeFromClient) {
    log.info("facade service verifying code from client {} {}", smsCodeFromClient, phoneNumber);
    if (verificationService.isPhoneNumberExistToVerifyByStatusZero(phoneNumber)) {
      final PhoneVerification phoneVerification = verificationService.getPhoneVerificationByPhoneAndCurrentDate(phoneNumber);
      phoneVerification.setStatus(SMS_STATUS_CHECKED);
      verificationService.updateSmsCodeAndPhoneNumber(phoneVerification);
      if (phoneVerification.getSmsCode().equals(smsCodeFromClient)) {
        return PhoneVerificationStatus.PHONE_IS_VALID.getStatus();
      } else {
        return PhoneVerificationStatus.PHONE_NUMBER_NOT_VALID.getStatus();
      }
    }
    log.info("error to verify client code {} {}", smsCodeFromClient, phoneNumber);
    return PhoneVerificationStatus.NO_PHONE_NUMBER_TO_VALIDATE.getStatus();
  }

  private void updatePhoneVerificationRequest(final String phoneNumber, final Integer smsCode, final PhoneVerification phoneVerification) {
    log.info("update db for phone {} count {}", phoneNumber, phoneVerification.getCount() + 1);
    phoneVerification.setSmsCode(smsCode);
    phoneVerification.setCount(phoneVerification.getCount() + 1);
    phoneVerification.setStatus(SMS_STATUS_UNCHECKED);
    verificationService.updateSmsCodeAndPhoneNumber(phoneVerification);
  }

}
