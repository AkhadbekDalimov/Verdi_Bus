package uz.asbt.digid.common.enums;

public enum PhotoVerificationStatus {

  PHOTOS_VALID(0),
  PHOTOS_INVALID(109),
  PHOTO_SIMILARITY_NOT_VALID(220),
  PHOTO_LIVENESS_NOT_VALID(221);

  private final int status;

  PhotoVerificationStatus(final int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }
}
