package uz.asbt.digid.loggerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.log.CrmLog;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrmRepository extends MongoRepository<CrmLog, String> {

    @Override
    <S extends CrmLog> S save(S entity);

    @Override
    Optional<CrmLog> findById(String s);

    @Override
    List<CrmLog> findAll();

    CrmLog findByGuid(String guid);

}
