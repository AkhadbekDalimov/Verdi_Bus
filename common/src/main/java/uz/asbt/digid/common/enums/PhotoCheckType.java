package uz.asbt.digid.common.enums;

public enum PhotoCheckType {

  CHECK(1),

  NOT_CHECK(0);

  private final int type;

  PhotoCheckType(final int type) {
    this.type = type;
  }

  public int getType() {
    return type;
  }
}
