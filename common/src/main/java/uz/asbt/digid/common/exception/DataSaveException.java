package uz.asbt.digid.common.exception;

public class DataSaveException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось сохранить сущность";

  @Override
  public int getCode() {
    return 1000020;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
