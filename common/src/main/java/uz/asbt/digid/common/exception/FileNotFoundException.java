package uz.asbt.digid.common.exception;

public class FileNotFoundException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось найти указанный файл в директории!";

  @Override
  public int getCode() {
    return 999;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
