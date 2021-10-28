package uz.asbt.digid.crmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.entity.Client;
import uz.asbt.digid.common.models.entity.Device;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByDeviceOrderByUpdatedDate(Device device);
    List<Client> findByDeviceAndMacAddress(Device device, String macAddress);
    List<Client> findByMacAddress(String macAddress);
    List<Client> findByMacAddressAndDeviceSerialNumber(String macAddress, String serialNumber);
    Optional<Client> findByDeviceSerialNumber(String serialNumber);


}
