package uz.asbt.digid.digidservice.service;

public interface SmsSendingService {

  void sendSmsCodeToPhoneNumber(String phoneNumber, Integer code);

  void informPersonAbtReachingMaxSmsRequest(String phoneNumber);
}
