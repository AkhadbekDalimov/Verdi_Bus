package uz.asbt.digid.common.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecognizeException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось выполнить операцию";

  @Override
  public int getCode() {
    return 604;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN;
  }

}
