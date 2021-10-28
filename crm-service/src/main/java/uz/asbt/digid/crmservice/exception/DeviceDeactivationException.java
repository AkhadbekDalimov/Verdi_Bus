package uz.asbt.digid.crmservice.exception;

import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

public class DeviceDeactivationException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось деактивировать устройство";

  @Override
  public int getCode() {
    return 9007;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
