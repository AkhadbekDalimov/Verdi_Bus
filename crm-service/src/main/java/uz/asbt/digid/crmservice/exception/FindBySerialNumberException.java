package uz.asbt.digid.crmservice.exception;

import lombok.RequiredArgsConstructor;
import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

@RequiredArgsConstructor
public class FindBySerialNumberException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось найти устройство по переданному серийному номеру «{}»";
  private final String serialNumber;

  @Override
  public int getCode() {
    return 9003;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN.replace("{}", serialNumber);
  }

}
