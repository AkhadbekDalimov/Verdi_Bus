package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.common.models.dto.DeviceDTO;

import java.util.Locale;

public interface IDeviceService {

  DeviceDTO findBySerialNumber(String serialNumber, Locale locale);

}
