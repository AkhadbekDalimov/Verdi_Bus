package uz.asbt.digid.crmservice.exception;

import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

public class OrganizationSaveException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось сохранить информацию об организации";

  @Override
  public int getCode() {
    return 9001;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
