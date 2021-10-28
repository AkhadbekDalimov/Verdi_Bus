package uz.asbt.digid.digidservice.service;

public interface IFacadePhoneVerificationService {

  int sendSmsToPhone(String phone);

  int checkSmsCodeAndPhone(String phoneNumber, Integer smsCodeFromClient);

}
