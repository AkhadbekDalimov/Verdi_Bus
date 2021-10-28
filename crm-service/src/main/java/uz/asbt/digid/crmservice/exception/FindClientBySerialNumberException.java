package uz.asbt.digid.crmservice.exception;

import lombok.RequiredArgsConstructor;
import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

@RequiredArgsConstructor
public class FindClientBySerialNumberException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось найти клиента по переданному серийному номеру устройства «{}»";
  private final String serialNumber;

  @Override
  public int getCode() {
    return 9014;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN.replace("{}", serialNumber);
  }

}
