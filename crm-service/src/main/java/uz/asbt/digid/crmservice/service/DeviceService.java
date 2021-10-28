package uz.asbt.digid.crmservice.service;

import org.springframework.data.domain.Pageable;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.rest.GridResponse;

import java.util.List;

public interface DeviceService {

    DeviceDTO save(DeviceDTO device);
    DeviceDTO update(DeviceDTO device);
    DeviceDTO findById(Long id);
    GridResponse<List<DeviceDTO>> findAll(Pageable pageable);
    DeviceDTO findBySerialNumber(String serialNumber);
    DeviceDTO findByOrgCrmId(String orgCrmId);
    DeviceDTO findByCrmId(String crmId);
    List<DeviceDTO> findAllByDeviceCrmId(List<String> ids);
    DeviceDTO activateDevice(DeviceDTO device);

}
