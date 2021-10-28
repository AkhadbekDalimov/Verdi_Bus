package uz.asbt.digid.crmservice.exception;

import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

public class OrganizationFindByCrmIdException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось найти устройство по переданному ID";

  @Override
  public int getCode() {
    return 9002;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
