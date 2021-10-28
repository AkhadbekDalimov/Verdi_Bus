package uz.asbt.digid.crmservice.exception;

import lombok.RequiredArgsConstructor;
import uz.asbt.digid.common.exception.AbstractBusinessLogicException;

@RequiredArgsConstructor
public class OrganizationFindException extends AbstractBusinessLogicException {

  private static final String PATTERN = "Не удалось найти запись об организации по переданному ID «{}»";
  private final Long id;

  @Override
  public int getCode() {
    return 9002;
  }

  @Override
  public String getInfoMessage() {
    return PATTERN.replace("{}", String.valueOf(id));
  }

}
