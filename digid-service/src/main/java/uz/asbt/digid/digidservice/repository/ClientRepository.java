package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.entity.Client;

import java.util.List;

/**
 * Created by KINS on 17.08.2021.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByAppId(String appId);
}
