package uz.asbt.digid.common.exception;

public class GettingListException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось получить список записей";

  @Override
  public int getCode() {
    return 9015;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
