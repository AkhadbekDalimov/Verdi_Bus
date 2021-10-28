package uz.asbt.digid.loggerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.log.MipPersonLog;

import java.util.List;
import java.util.Optional;

@Repository
public interface MipPersonRepository extends MongoRepository<MipPersonLog, String> {

    @Override
    <S extends MipPersonLog> S save(S entity);

    @Override
    Optional<MipPersonLog> findById(String s);

    @Override
    List<MipPersonLog> findAll();

    MipPersonLog findByGuid(String guid);

}
