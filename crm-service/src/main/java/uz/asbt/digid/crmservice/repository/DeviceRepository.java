package uz.asbt.digid.crmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.entity.Device;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    Device findBySerialNumber(String serialNumber);
    Device findByOrgCrmId(String orgCrmId);
    Device findByDeviceCrmId(String crmId);
    List<Device> findByDeviceCrmIdIn(List<String> ids);

}
