package uz.asbt.digid.common.exception;

public class NoFaceException extends AbstractBusinessLogicException {
  private static final String PATTERN = "Не удалось распознать лицо";

  @Override
  public int getCode() {
    return 602;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }
}
