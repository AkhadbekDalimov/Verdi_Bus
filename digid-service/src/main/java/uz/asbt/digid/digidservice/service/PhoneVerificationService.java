package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.entity.PhoneVerification;

public interface PhoneVerificationService {

  PhoneVerification saveSmsCodeAndPhoneNumber(String phoneNumber, Integer smsCode);

  PhoneVerification updateSmsCodeAndPhoneNumber(PhoneVerification phoneVerification);

  PhoneVerification getPhoneVerificationByPhoneAndCurrentDate(String phoneNumber);

  boolean isPhoneNumberExistInDBWithCurrentDate(String phoneNumber);

  boolean isPhoneNumberExistToVerifyByStatusZero(String phoneNumber);
}
