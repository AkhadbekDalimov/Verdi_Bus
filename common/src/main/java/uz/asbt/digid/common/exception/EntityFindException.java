package uz.asbt.digid.common.exception;

public class EntityFindException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось найти сущность";

  @Override
  public int getCode() {
    return 9005;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
