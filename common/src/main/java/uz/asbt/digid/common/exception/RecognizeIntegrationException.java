package uz.asbt.digid.common.exception;

public class RecognizeIntegrationException extends AbstractBusinessLogicException {
  private static final String PATTERN = "Не удалось получить данные из сервиса";

  @Override
  public int getCode() {
    return 605;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }
}
