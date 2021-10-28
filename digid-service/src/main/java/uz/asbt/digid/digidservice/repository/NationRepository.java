package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.digidservice.model.entity.Nation;

@Repository
public interface NationRepository extends JpaRepository<Nation, Long> {

    @Query("SELECT n FROM Nation n WHERE n.lat = ?1")
    Nation findByName(String name);
    Nation findByBankCode(String icaoCode);

}
