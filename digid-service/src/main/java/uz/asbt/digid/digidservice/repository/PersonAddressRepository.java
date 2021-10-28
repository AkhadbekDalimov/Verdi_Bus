package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.asbt.digid.digidservice.model.entity.PersonAddress;

import java.util.List;

public interface PersonAddressRepository extends JpaRepository<PersonAddress, Long> {

  @Query(value = "SELECT * FROM PERSON_ADDRESS pa JOIN PASSPORT_DATA pd ON pa.PASSPORT_DATA_ID = pd.PASSPORT_DATA_ID " +
    "WHERE pd.DOCUMENT_SERIAL_NUMBER =?1", nativeQuery = true)
  List<PersonAddress> findAllByDocSerialNumber(String serialNumber);

  @Query(value = "SELECT * FROM PERSON_ADDRESS pa JOIN PASSPORT_DATA pd ON pa.PASSPORT_DATA_ID = pd.PASSPORT_DATA_ID " +
    "WHERE pd.DOCUMENT_SERIAL_NUMBER =?1 AND pa.ADDRESS=?2 AND ROWNUM = 1", nativeQuery = true)
  PersonAddress findByDocSerialNumberAndAddress(String serialNumber, String address);
}
