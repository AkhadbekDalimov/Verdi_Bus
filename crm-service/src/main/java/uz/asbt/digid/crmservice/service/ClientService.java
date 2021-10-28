package uz.asbt.digid.crmservice.service;

import org.springframework.data.domain.Pageable;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.rest.GridResponse;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    ClientDTO save(ClientDTO client);
    ClientDTO findById(Long id);
    GridResponse<List<ClientDTO>> findAll(Pageable pageable);
    ClientDTO findByDevice(DeviceDTO device);
    ClientDTO findByDeviceAndMacAddress(DeviceDTO device, String macAddress);
    Optional<ClientDTO> findByMacAddress(String macAddress);
    Optional<ClientDTO> findByMacAddressAndSerialNumber(String macAddress, String serialNumber);

    void deleteClientByDeviceSerialNumber(DeviceDTO device);
}
