package uz.asbt.digid.updaterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.entity.Client;

import java.util.Optional;

/**
 * Created by User on 17.01.2020.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{


    Optional<Client> findClientByDeviceSerialNumber(String serialNumber);

}
