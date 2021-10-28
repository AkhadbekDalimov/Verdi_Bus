package uz.asbt.digid.crmservice.exception;

import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

public class ProviderSaveException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось сохранить поставщика";

  @Override
  public int getCode() {
    return 9013;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
