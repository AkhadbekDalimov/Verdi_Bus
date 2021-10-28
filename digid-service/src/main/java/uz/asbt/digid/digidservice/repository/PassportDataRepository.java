package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.digidservice.model.entity.PassportData;

import java.util.List;

@Repository
public interface PassportDataRepository extends JpaRepository<PassportData, Long> {

  @Query(value = "SELECT * FROM PASSPORT_DATA pd JOIN PINPP p ON pd.PINPP_ID = p.PINPP_ID WHERE p.PINPP = ?1", nativeQuery = true)
  List<PassportData> findPassportDataByPinpp(String pinpp);

  PassportData findByDocumentSerialNumberAndBirthDate(String documentSeriaNumber, String birthDate);

  PassportData findByDocumentSerialNumber(String documentSerialNumber);

}
