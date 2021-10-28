package uz.asbt.digid.loggerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.log.MipGniLog;

import java.util.List;
import java.util.Optional;

@Repository
public interface MipGniRepository extends MongoRepository<MipGniLog, String> {

    @Override
    <S extends MipGniLog> S save(S entity);

    @Override
    Optional<MipGniLog> findById(String s);

    @Override
    List<MipGniLog> findAll();

    MipGniLog findByGuid(String guid);

}
