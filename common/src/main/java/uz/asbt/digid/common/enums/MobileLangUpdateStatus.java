package uz.asbt.digid.common.enums;

public enum  MobileLangUpdateStatus {

  LANG_UPDATED(0),
  ALL_LANG_NEED_UPDATE(1),
  UPDATE_NEW_FILES(2);

  private final int status;

  MobileLangUpdateStatus(int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }
}
