package uz.asbt.digid.common.exception;

public class BadImageFormatException extends AbstractBusinessLogicException {
  private static final String PATTERN = "Не верный формат фотографии требуется (JPEG или PNG)";

  @Override
  public int getCode() {
    return 601;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }
}
