package uz.asbt.digid.crmservice.service.impl;

import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.DeviceDTO;

public class TestDataProvider {

  public static final DeviceDTO DEVICE = DeviceDTO.builder()
    .deviceCrmId("test crm id")
    .serialNumber("32238155")
    .status(1)
    .deviceTypeId(2)
    .orgCrmId("test org crm id")
    .livenessCheck(1)
    .similarityCheck(1)
    .build();

  public static final ClientDTO CLIENT = ClientDTO.builder()
    .clientPubKey("public key")
    .device(DEVICE)
    .home("33A")
    .ipAddress("192.168.1.1")
    .macAddress("testmacadd")
    .street("test st")
    .build();
}
