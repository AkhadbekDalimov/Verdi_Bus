package uz.asbt.digid.crmservice.exception;

import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

public class DeviceDuplicateException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Эта запись существует в базе данных!";

  @Override
  public int getCode() {
    return 9000;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
