package uz.asbt.digid.common.models.entity.client;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelMobileData {

  @JsonProperty("DeviceId")
  @JsonAlias("deviceId")
  private String deviceCrmId;

  @JsonProperty("SN")
  @JsonAlias("sn")
  private String serialNumber;

  @JsonProperty("DeviceType")
  @JsonAlias("deviceType")
  private Integer deviceTypeId;

  @JsonProperty("Status")
  @JsonAlias("status")
  private Integer status;

  @JsonProperty("MobileType")
  @JsonAlias("mobileType")
  private String mobileType;

  @JsonProperty("MobileNumber")
  @JsonAlias("mobileNumber")
  private String mobileNumber;

  @JsonProperty("MobileDeviceId")
  @JsonAlias("mobileDeviceId")
  private String mobileDeviceId;

  @JsonProperty("Email")
  @JsonAlias("email")
  private String email;

  public String getMobileType() {
    return mobileType;
  }

  public void setMobileType(String mobileType) {
    this.mobileType = mobileType;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getMobileDeviceId() {
    return mobileDeviceId;
  }

  public void setMobileDeviceId(String mobileDeviceId) {
    this.mobileDeviceId = mobileDeviceId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "ModelMobileData{" +
      " deviceCrmId='" + deviceCrmId + '\'' +
      ", serialNumber='" + serialNumber + '\'' +
      ", deviceTypeId=" + deviceTypeId +
      ", status=" + status +
      ", mobileType='" + mobileType + '\'' +
      ", mobileNumber='" + mobileNumber + '\'' +
      ", mobileDeviceId='" + mobileDeviceId + '\'' +
      ", email='" + email + '\'' +
      '}';
  }
}
