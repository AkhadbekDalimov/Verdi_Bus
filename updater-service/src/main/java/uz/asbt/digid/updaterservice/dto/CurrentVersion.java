package uz.asbt.digid.updaterservice.dto;

public interface CurrentVersion {

  String getDeviceSerialNumber();
  String getCurrentClientVersion();
  String getSignString();

}
