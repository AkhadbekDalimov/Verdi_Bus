package uz.asbt.digid.loggerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.log.MipAddressLog;

import java.util.List;
import java.util.Optional;

@Repository
public interface MipAddressRepository extends MongoRepository<MipAddressLog, String> {

    @Override
    <S extends MipAddressLog> S save(S entity);

    @Override
    Optional<MipAddressLog> findById(String s);

    @Override
    List<MipAddressLog> findAll();

    MipAddressLog findByGuid(String guid);

}
