package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.digidservice.model.entity.Sex;

import java.util.Optional;

@Repository
public interface SexRepository extends JpaRepository<Sex, Long> {

    Optional<Sex> findByIcaoCode(String icaoCode);
    Optional<Sex> findByBankCode(long bankCode);

}
