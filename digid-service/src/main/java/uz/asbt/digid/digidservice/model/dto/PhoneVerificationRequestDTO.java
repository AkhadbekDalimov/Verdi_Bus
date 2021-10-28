package uz.asbt.digid.digidservice.model.dto;

import lombok.Data;

@Data
public class PhoneVerificationRequestDTO {

  private String phoneNumber;
  private Integer smsCode;
}
