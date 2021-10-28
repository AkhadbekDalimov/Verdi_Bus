package uz.asbt.digid.updaterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.asbt.digid.common.models.entity.Device;

import java.util.Optional;

/**
 * Created by User on 17.01.2020.
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findDeviceBySerialNumber(String serialNumber);

}
