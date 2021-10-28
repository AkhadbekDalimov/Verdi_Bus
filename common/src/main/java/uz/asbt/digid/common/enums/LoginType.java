package uz.asbt.digid.common.enums;

public enum  LoginType {
  REGISTRATION(1),

  VERIFICATION(2);

  private final int type;

  LoginType(final int type) {
    this.type= type;
  }

  public int getType() {
    return type;
  }
}
