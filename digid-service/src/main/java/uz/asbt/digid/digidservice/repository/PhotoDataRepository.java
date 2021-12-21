package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.digidservice.model.entity.PhotoData;

import java.util.Optional;

@Repository
public interface PhotoDataRepository extends JpaRepository<PhotoData, Long> {

  PhotoData findByPassportDataId(Long id);

  @Query(value = "SELECT * FROM PHOTO_DATA ph " +
          "JOIN PASSPORT_DATA pa ON ph.PASSPORT_DATA_ID = pa.PASSPORT_DATA_ID " +
          "WHERE pa.DOCUMENT_SERIAL_NUMBER =?1 and ph.PHOTO IS not NULL",nativeQuery = true)
  Optional<PhotoData> findByPassportDataId(String docSerialNumber);

}
