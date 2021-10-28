package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.digidservice.model.entity.DocReadData;

import java.util.List;

@Repository
public interface DocReadDataRepository extends JpaRepository<DocReadData, Long> {

  @Query(value = "SELECT * FROM DOC_READ_DATA drd JOIN PASSPORT_DATA pd ON drd.PASSPORT_DATA_ID=pd.PASSPORT_DATA_ID " +
    " WHERE pd.PINPP_ID=?1", nativeQuery = true)
//  @Query(value = "SELECT * FROM DOC_READ_DATA drd JOIN PASSPORT_DATA pd ON drd.PASSPORT_DATA_ID=pd.PASSPORT_DATA_ID " +
//    "JOIN PINPP p ON pd.PINPP_ID=p.PINPP_ID WHERE p.PINPP=?1 ", nativeQuery = true)
  List<DocReadData> findAllByPinpp(String pinpp);
}
