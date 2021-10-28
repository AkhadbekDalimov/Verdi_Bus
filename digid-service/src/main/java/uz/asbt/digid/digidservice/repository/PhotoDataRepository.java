package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.digidservice.model.entity.PhotoData;

@Repository
public interface PhotoDataRepository extends JpaRepository<PhotoData, Long> {

  PhotoData findByPassportDataId(Long id);

}
