package uz.asbt.digid.crmservice.service.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.asbt.digid.common.enums.DeviceStatus;
import uz.asbt.digid.common.enums.ValidationTypeEnum;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.dto.ValidationScoresDTO;
import uz.asbt.digid.common.models.entity.Device;
import uz.asbt.digid.common.models.entity.Organization;
import uz.asbt.digid.common.models.rest.GridResponse;
import uz.asbt.digid.common.service.ErrorService;
import uz.asbt.digid.crmservice.exception.DeviceDuplicateException;
import uz.asbt.digid.crmservice.exception.DeviceSaveException;
import uz.asbt.digid.crmservice.repository.ClientRepository;
import uz.asbt.digid.crmservice.repository.DeviceRepository;
import uz.asbt.digid.crmservice.service.ClientService;
import uz.asbt.digid.crmservice.service.DeviceService;
import uz.asbt.digid.crmservice.service.OrganizationService;
import uz.asbt.digid.crmservice.util.RendipScoresProvider;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Transactional
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DeviceServiceImpl implements DeviceService {

  private final DeviceRepository deviceRepository;
  private final OrganizationService organizationService;
  private final ModelMapper modelMapper;
  private final ClientRepository clientRepository;
  private final ClientService clientService;
  private final RendipScoresProvider rendipScoresProvider;
  private final ErrorService error;
  @Override
  public DeviceDTO save(@NonNull final DeviceDTO device) {
    return Optional.of(device)
      .map(d -> {
        log.info("Begin getting device by CRMID");
        Optional.ofNullable(deviceRepository.findByDeviceCrmId(d.getDeviceCrmId()))
          .ifPresent(obj -> {
            log.info("Device is present{}{}", obj, obj.getSerialNumber().contains("RMD"));
            if ((obj.getStatus() != DeviceStatus.INTO_STORE.getStatus()
              || obj.getStatus() != DeviceStatus.RETURN.getStatus())
              && !(obj.getSerialNumber().contains("RMD"))) {
              throw new DeviceDuplicateException();
            }
            d.setId(obj.getId());
          });
        log.info("Device: {}", d);
        return d;
      })
      .map(d -> {
        final Device result = modelMapper.map(d, Device.class);
        result.setOrganization(getOrganization(d));
        log.info("Device result: {}", result);
        return result;
      })
      .map(deviceRepository::save)
      .map(d -> modelMapper.map(d, DeviceDTO.class))
      .orElseThrow(EntityFindException::new);
  }

  private Organization getOrganization(final DeviceDTO device) {
    if (device.getOrgCrmId() != null) {
      return modelMapper.map(organizationService.findByOrgCrmId(device.getOrgCrmId()), Organization.class);
    }
    log.info("Device organization is NULL");
    throw new CustomException(error.getErrorCode("ex9002"), error.getErrorMessage("message_9002"));
  }

  @Override
  public DeviceDTO update(@NonNull final DeviceDTO device) {
    final Device dev = Optional.of(device)
      .filter(d -> d.getId() != 0)
      .map(d -> deviceRepository.findById(d.getId()).orElseThrow(EntityFindException::new))
      .orElseGet(() -> deviceRepository.findByDeviceCrmId(device.getDeviceCrmId()));

    final DeviceDTO res = Optional.ofNullable(dev)
      .map(d -> {
        device.setId(d.getId());
        return device;
      })
      .map(d -> {
        final Device result = modelMapper.map(device, Device.class);
        result.setOrganization(getOrganization(d));
        return result;
      })
      .map(deviceRepository::save)
      .map(d -> modelMapper.map(d, DeviceDTO.class))
      .orElseThrow(DeviceSaveException::new);

    if (dev.getStatus() != device.getStatus()) {
      clientService.deleteClientByDeviceSerialNumber(device);
    }

    return res;
  }

  @Override
  public DeviceDTO findById(@NonNull final Long id) {
    return deviceRepository.findById(id)
      .map(device -> modelMapper.map(device, DeviceDTO.class))
      .orElseThrow(EntityFindException::new);
  }

  @Override
  public GridResponse<List<DeviceDTO>> findAll(@NonNull final Pageable pageable) {
    return Optional.of(pageable)
      .map(deviceRepository::findAll)
      .map(devices -> GridResponse
        .<List<DeviceDTO>>builder()
        .list(devices.getContent().stream().map(i -> modelMapper.map(i, DeviceDTO.class)).collect(toList()))
        .totalPages(devices.getTotalPages())
        .totalItemsCount(devices.getTotalElements())
        .build())
      .orElseThrow(EntityFindException::new);
  }

  @Override
  public DeviceDTO findBySerialNumber(@NonNull final String serialNumber) {
    return Optional.of(serialNumber)
      .map(deviceRepository::findBySerialNumber)
      .map(device -> {
        final DeviceDTO deviceDTO = modelMapper.map(device, DeviceDTO.class);
        deviceDTO.setValidationScores(ValidationScoresDTO
          .builder()
          .rendipMinLivenessScore(rendipScoresProvider.getLivenessScore())
          .rendipMinSimilarityScore(rendipScoresProvider.getSimilarityScore())
          .build());
        deviceDTO.setLiveness(deviceDTO.getLivenessCheck() == ValidationTypeEnum.CHECK.getType());
        return deviceDTO;
      })
      .orElseThrow(EntityFindException::new);
  }

  @Override
  public DeviceDTO findByOrgCrmId(@NonNull final String orgCrmId) {
    return Optional.of(orgCrmId)
      .map(deviceRepository::findByOrgCrmId)
      .map(device -> modelMapper.map(device, DeviceDTO.class))
      .orElseThrow(EntityFindException::new);
  }

  @Override
  public DeviceDTO findByCrmId(@NonNull final String crmId) {
    return Optional.of(crmId)
      .map(deviceRepository::findByDeviceCrmId)
      .map(device -> modelMapper.map(device, DeviceDTO.class))
      .orElseThrow(EntityFindException::new);
  }

  @Override
  public List<DeviceDTO> findAllByDeviceCrmId(@NonNull final List<String> ids) {
    return Optional.of(ids)
      .map(deviceRepository::findByDeviceCrmIdIn)
      .map(devices -> devices.stream().map(d -> modelMapper.map(d, DeviceDTO.class)).collect(toList()))
      .orElseThrow(EntityFindException::new);
  }

  public DeviceDTO activateDevice(final DeviceDTO device) {
    return Optional.ofNullable(device)
      .map(d -> modelMapper.map(d, Device.class))
      .map(d -> {
        d.setStatus(DeviceStatus.ACTIVE.getStatus());
        return d;
      })
      .map(deviceRepository::save)
      .map(d -> modelMapper.map(d, DeviceDTO.class))
      .orElseThrow(EntityFindException::new);
  }
}
