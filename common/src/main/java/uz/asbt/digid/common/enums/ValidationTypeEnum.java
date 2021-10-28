package uz.asbt.digid.common.enums;

public enum ValidationTypeEnum {

  CHECK(1),
  NOT_CHECK(0);

  private final int type;

  ValidationTypeEnum(final int type) {
    this.type = type;
  }

  public int getType() {
    return type;
  }
}
