package uz.asbt.digid.digidservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.digidservice.model.entity.DocType;

import java.util.Optional;

@Repository
public interface DocTypeRepository extends JpaRepository<DocType, Long> {

    Optional<DocType> findByIcaoCode(String icaoCode);
    Optional<DocType> findByBankCode(long bankCode);

}
