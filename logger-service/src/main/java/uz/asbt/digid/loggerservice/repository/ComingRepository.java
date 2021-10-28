package uz.asbt.digid.loggerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.log.ComingLog;

@Repository
public interface ComingRepository extends MongoRepository<ComingLog, String> {
}
