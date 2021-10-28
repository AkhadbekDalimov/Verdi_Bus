package uz.asbt.digid.crmservice.exception;

import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

public class DeviceActivationException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось активировать устройство";

  @Override
  public int getCode() {
    return 9008;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
