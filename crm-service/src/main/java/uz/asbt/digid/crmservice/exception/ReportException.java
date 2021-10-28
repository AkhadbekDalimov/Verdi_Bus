package uz.asbt.digid.crmservice.exception;

import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

public class ReportException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось получить отчет";

  @Override
  public int getCode() {
    return 9016;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
