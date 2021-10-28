package uz.asbt.digid.common.enums;

public enum PhoneVerificationStatus {

  SMS_CODE_SEND(112),
  EXCEEDED_MAX_COUNT(113),
  PHONE_NUMBER_NOT_VALID(114),
  PHONE_IS_VALID(115),
  NO_PHONE_NUMBER_TO_VALIDATE(116),
  ERROR_VALIDATE_CODE(117);

  private final int status;

  PhoneVerificationStatus(final int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }
}
