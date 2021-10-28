package uz.asbt.digid.crmservice.exception;

import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

public class ClientSaveException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось сохранить информацию о клиенте";

  @Override
  public int getCode() {
    return 9009;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
