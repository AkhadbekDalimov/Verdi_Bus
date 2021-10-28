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
import uz.asbt.digid.common.enums.IsDeleted;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.entity.Client;
import uz.asbt.digid.common.models.entity.Device;
import uz.asbt.digid.common.models.rest.GridResponse;
import uz.asbt.digid.crmservice.repository.ClientRepository;
import uz.asbt.digid.crmservice.repository.DeviceRepository;
import uz.asbt.digid.crmservice.service.ClientService;
import uz.asbt.digid.crmservice.util.ClientUtil;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Transactional
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final DeviceRepository deviceRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ClientDTO save(@NonNull final ClientDTO client) {
        log.info("begin saving client {}{}", client,clientRepository);
        return Optional.of(client.getDevice())
          .map(d -> deviceRepository.findBySerialNumber(d.getSerialNumber()))
          .map(d -> modelMapper.map(d, DeviceDTO.class))
          .map(d -> {
              client.setDevice(d);
              return client;
          })
          .map(c -> {
              final Client obj = modelMapper.map(c, Client.class);
              obj.setIsDeleted(0);
              return obj;
          })
          .map(clientRepository::save)
          .map(c -> modelMapper.map(c, ClientDTO.class))
          .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public ClientDTO findById(@NonNull final Long id) {
        return Optional.of(id)
          .map(clientRepository::findById)
          .map(client -> modelMapper.map(client, ClientDTO.class))
          .orElseThrow(EntityFindException::new);
    }

    @Override
    public GridResponse<List<ClientDTO>> findAll(@NonNull final Pageable pageable) {
        return Optional.of(pageable)
          .map(clientRepository::findAll)
          .map(clients -> GridResponse
            .<List<ClientDTO>>builder()
            .list(clients.getContent().stream().map(i -> modelMapper.map(i, ClientDTO.class)).collect(toList()))
            .totalItemsCount(clients.getTotalElements())
            .totalPages(clients.getTotalPages())
            .build())
          .orElseThrow(EntityFindException::new);
    }

    @Override
    public ClientDTO findByDevice(@NonNull final DeviceDTO device) {
        return Optional.of(device)
          .map(d -> modelMapper.map(d, Device.class))
          .flatMap(res -> ClientUtil.getNotDeletedClient(clientRepository.findByDeviceOrderByUpdatedDate(res)))
          .map(c -> modelMapper.map(c, ClientDTO.class))
          .orElseThrow(EntityFindException::new);
    }

    @Override
    public ClientDTO findByDeviceAndMacAddress(@NonNull final DeviceDTO device, final String macAddress) {
        return Optional.ofNullable(macAddress)
          .map(mac -> {
              final Device d = modelMapper.map(device, Device.class);
              return ClientUtil.getNotDeletedClient(clientRepository.findByDeviceAndMacAddress(d, mac));
          })
          .map(client -> modelMapper.map(client, ClientDTO.class))
          .orElseThrow(EntityFindException::new);
    }

    @Transactional
    @Override
    public Optional<ClientDTO> findByMacAddress(final String macAddress) {
        return Optional.ofNullable(macAddress)
          .flatMap(mac -> ClientUtil.getNotDeletedClient(clientRepository.findByMacAddress(mac)))
          .map(client -> modelMapper.map(client, ClientDTO.class));
    }

    @Override
    public Optional<ClientDTO> findByMacAddressAndSerialNumber(final String macAddress, final String serialNumber) {
        return Optional.ofNullable(macAddress)
          .flatMap(addr -> Optional.ofNullable(serialNumber))
          .flatMap(sn -> ClientUtil.getNotDeletedClient(clientRepository.findByMacAddressAndDeviceSerialNumber(macAddress, sn)))
          .map(client -> modelMapper.map(client, ClientDTO.class));
    }

    // set client is deleted 1 by device status
    public void deleteClientByDeviceSerialNumber(final DeviceDTO device) {
        log.info("set status is deleted to client by serial number {}", device.getSerialNumber());
        final Device dev = Optional.of(device)
          .filter(d -> d.getId() != 0)
          .map(d -> deviceRepository.findById(d.getId()).orElseThrow(EntityFindException::new))
          .orElseGet(() -> deviceRepository.findByDeviceCrmId(device.getDeviceCrmId()));
        log.info("dev info {}", dev);
        if(dev != null && ((dev.getStatus() == DeviceStatus.ACTIVE.getStatus() && dev.isTest())
          || dev.getStatus() == DeviceStatus.DISTRIBUTED.getStatus()
          || dev.getStatus() == DeviceStatus.INTO_STORE.getStatus()
          || dev.getStatus() == DeviceStatus.CHANGE.getStatus()
          || dev.getStatus() == DeviceStatus.RETURN.getStatus()
          || dev.getStatus() == DeviceStatus.OFF.getStatus())
          || device.getStatus() == DeviceStatus.MOBILE_REGISTRATION.getStatus()) {
            clientRepository.findByDeviceOrderByUpdatedDate(dev)
              .stream()
              .filter(c -> c.getIsDeleted() == IsDeleted.NOT_DELETED.getCode())
              .forEach(c -> {
                  c.setIsDeleted(IsDeleted.DELETED.getCode());
                  log.info("delete client {}", c);
                  clientRepository.save(c);
              });
        }
    }



}
