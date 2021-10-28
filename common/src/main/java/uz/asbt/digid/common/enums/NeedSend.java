package uz.asbt.digid.common.enums;

public enum NeedSend {
  SEND(1),

  NOT_SEND(0);

  private final int code;

  NeedSend(final int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
