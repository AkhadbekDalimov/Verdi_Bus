package uz.asbt.digid.common.exception;

public interface BusinessLogicException {
  default int getCode() { return 9999; }
  String getInfoMessage();
}
