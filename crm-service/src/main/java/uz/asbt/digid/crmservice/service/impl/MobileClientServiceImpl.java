package uz.asbt.digid.crmservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.enums.DeviceStatus;
import uz.asbt.digid.common.enums.ValidationTypeEnum;
import uz.asbt.digid.common.exception.DataSaveException;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.CrmMobileRegRequest;
import uz.asbt.digid.common.models.dto.CrmMobileRegResponse;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.dto.OrganizationDTO;
import uz.asbt.digid.crmservice.service.ClientService;
import uz.asbt.digid.crmservice.service.DeviceService;
import uz.asbt.digid.crmservice.service.MobileClientService;
import uz.asbt.digid.crmservice.service.OrganizationService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MobileClientServiceImpl implements MobileClientService {

  private final ClientService clientService;
  private final DeviceService deviceService;
  private final OrganizationService organizationService;
  private final CRMIntegrationService crmIntegrationService;
  private final ModelMapper modelMapper;

  @Override
  public ClientDTO sendMobileClientDataToCrm(final CrmMobileRegRequest crmMobileRegRequest) {
    try {
      final CrmMobileRegResponse mobileRegResponse = crmIntegrationService.sendMobileDataToCRM(crmMobileRegRequest);
      log.info("begin saving person");
      final OrganizationDTO organizationDTO = Optional.ofNullable(modelMapper.map(mobileRegResponse, OrganizationDTO.class))
        .map(org -> {
          org.setPhoneNumber(crmMobileRegRequest.getMobileData().getMobileNumber());
          return org;
        })
        .map(organizationService::save)
        .orElseThrow(DataSaveException::new);

      log.info("begin saving mobile device");
      final DeviceDTO deviceDTO = Optional.ofNullable(modelMapper.map(mobileRegResponse.getMobileData(), DeviceDTO.class))
        .map(dev -> {
          dev.setOrgCrmId(organizationDTO.getOrgCrmId());
          dev.setOrganization(organizationDTO);
          dev.setLivenessCheck(ValidationTypeEnum.CHECK.getType());
          dev.setSimilarityCheck(ValidationTypeEnum.CHECK.getType());
          return dev;
        })
        .map(deviceService::save)
        .orElseThrow(DataSaveException::new);

      if(deviceDTO.getSerialNumber().startsWith("RMD")){
        log.info("begin delete clients by serial number {}", deviceDTO);
        deviceDTO.setStatus(DeviceStatus.MOBILE_REGISTRATION.getStatus());
        clientService.deleteClientByDeviceSerialNumber(deviceDTO);
      }
      log.info("begin saving client");
      return Optional.ofNullable(modelMapper.map(crmMobileRegRequest.getModelAddress(), ClientDTO.class))
        .map(client -> {
          client.setClientPubKey(mobileRegResponse.getClientPubKey());
          client.setDevice(deviceDTO);
          log.info("client for save{}", client);
          return client;
        })
        .map(c -> {

          final ClientDTO dto = clientService.save(c);
          log.info("client result {}", dto);
          return dto;
        })
        .orElseThrow(DataSaveException::new);

    } catch (final Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }
}