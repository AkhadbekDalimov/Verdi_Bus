package uz.asbt.digid.common.exception;

public class UniqueIdentificatorException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Уникальный идентификатор ID должен быть указан";

  @Override
  public int getCode() {
    return 9006;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
