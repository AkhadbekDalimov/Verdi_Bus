package uz.asbt.digid.common.enums;

public enum  DeviceSerialType {

  MOBILE_CLIENT("RMD"),
  READER_LESS("RLM");

  private final String type;

  DeviceSerialType(final String code) {
    this.type = code;
  }

  public String getType() {
    return type;
  }
}
