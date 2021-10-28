package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.digidservice.model.entity.District;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    Optional<District> findByBankCode(long bankCode);
    Optional<District> findBySoatoCode(String soatoCode);
    Optional<District> findByIdAndRegion(long id, long region);
    Optional<District> findByIdAndRegionBankCode(long id, long bankCode);

}
