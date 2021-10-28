package uz.asbt.digid.crmservice.exception;

import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

public class DeviceSaveException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось найти устройство по переданному ID";

  @Override
  public int getCode() {
    return 9005;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
