package uz.asbt.digid.crmservice.service.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.asbt.digid.common.enums.DeviceStatus;
import uz.asbt.digid.common.enums.SendStatus;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.crmservice.exception.ClientSaveException;
import uz.asbt.digid.crmservice.repository.ClientRepository;
import uz.asbt.digid.crmservice.service.ClientService;
import uz.asbt.digid.crmservice.service.DeviceService;
import uz.asbt.digid.crmservice.util.ClientUtil;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class RegistrationService {

  private final ClientService clientService;
  private final ClientRepository clientRepository;
  private final CrmNotificationService notificationService;
  private final DeviceService deviceService;
  private final ModelMapper modelMapper;

  @Transactional
  public ClientDTO register(final ClientDTO client) {

    clientService.deleteClientByDeviceSerialNumber(client.getDevice());

    return Optional.ofNullable(client)
      .map(c -> ClientUtil.getNotDeletedClient(clientRepository.findByMacAddressAndDeviceSerialNumber(c.getMacAddress(), c.getDevice().getSerialNumber()))
        .map(res -> {
          log.info("Client is not NULL deviceId is {}", res.getDevice());
          log.info("Client is  {}", res);
          client.setId(res.getId());
          return Optional.ofNullable(clientService.save(client)).orElseThrow(ClientSaveException::new);
        })
        .orElseGet(() -> {
          log.info("Client is NULL deviceId is {}", client.getDevice());
          return Optional.ofNullable(clientService.save(client)).orElseThrow(ClientSaveException::new);
        }))
      .map(result -> {
        result.getDevice().setStatus(DeviceStatus.ACTIVE.getStatus());
        deviceService.activateDevice(result.getDevice());
        notificationService.sendNewStatus(result, SendStatus.NEW);
        return result;
      })
      .orElseThrow(ClientSaveException::new);
  }

  public ClientDTO findClient(@NonNull final String serialNumber) {
    return Optional.of(serialNumber)
      .map(deviceService::findBySerialNumber)
      .map(clientService::findByDevice)
      .map(client -> modelMapper.map(client, ClientDTO.class))
      .orElseThrow(EntityFindException::new);
  }

}
