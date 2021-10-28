package uz.asbt.digid.loggerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.log.Income;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends MongoRepository<Income, String> {

    @Override
    <S extends Income> S save(S entity);

    @Override
    Optional<Income> findById(String s);

    @Override
    List<Income> findAll();

    Income findBySerialNumber(String serialNumber);

    Income findByGuid(String guid);
}
