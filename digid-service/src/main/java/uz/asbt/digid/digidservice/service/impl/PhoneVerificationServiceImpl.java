package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.asbt.digid.digidservice.model.entity.PhoneVerification;
import uz.asbt.digid.digidservice.repository.PhoneVerificationRepository;
import uz.asbt.digid.digidservice.service.PhoneVerificationService;

import static uz.asbt.digid.digidservice.util.SmsUtils.getCurrentDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneVerificationServiceImpl implements PhoneVerificationService {

  private final PhoneVerificationRepository repository;
  private static final int SMS_STATUS_UNCHECKED = 0;

  @Override
  public PhoneVerification saveSmsCodeAndPhoneNumber(final String phoneNumber, final Integer smsCode) {
    log.info("begin saving sms code request {}", phoneNumber);
    return repository.save(buildNewPhoneVerification(phoneNumber, smsCode));
  }

  private PhoneVerification buildNewPhoneVerification(final String phoneNumber, final Integer smsCode) {
    return PhoneVerification.builder()
      .count(1)
      .smsCode(smsCode)
      .phoneNumber(phoneNumber)
      .localDateTime(getCurrentDate())
      .build();
  }

  @Override
  public PhoneVerification updateSmsCodeAndPhoneNumber(final PhoneVerification phoneVerification) {
    log.info("update sms code request {}", phoneVerification);
    return repository.save(phoneVerification);
  }

  @Override
  public PhoneVerification getPhoneVerificationByPhoneAndCurrentDate(final String phoneNumber) {
    log.info("get phone verification from db by phone number {}", phoneNumber);
    return repository.getByPhoneNumberAndLocalDateTime(phoneNumber, getCurrentDate());
  }

  @Override
  public boolean isPhoneNumberExistInDBWithCurrentDate(final String phoneNumber) {
    log.info("check existence  phone number {}", phoneNumber);
    return repository.existsByPhoneNumberAndLocalDateTime(phoneNumber, getCurrentDate());
  }

  @Override
  public boolean isPhoneNumberExistToVerifyByStatusZero(final String phoneNumber) {
    log.info("check existence  phone number {}", phoneNumber);
    return repository.existsByPhoneNumberAndStatus(phoneNumber, SMS_STATUS_UNCHECKED);
  }
}
