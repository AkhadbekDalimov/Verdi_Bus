package uz.asbt.digid.loggerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.log.Integration;

import java.util.List;
import java.util.Optional;

@Repository
public interface IntegrationRepository extends MongoRepository<Integration, String> {

    @Override
    <S extends Integration> S save(S entity);

    @Override
    Optional<Integration> findById(String s);

    @Override
    List<Integration> findAll();

    Integration findBySerialNumber(String serialNumber);

    Integration findByGuid(String guid);
}
