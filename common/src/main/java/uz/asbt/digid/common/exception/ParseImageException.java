package uz.asbt.digid.common.exception;

public class ParseImageException extends AbstractBusinessLogicException {
  private static final String PATTERN = "Не удалось распарсить фото";

  @Override
  public int getCode() {
    return 603;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }
}
