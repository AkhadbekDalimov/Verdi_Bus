package uz.asbt.digid.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.dto.PersonDocReadData;
import uz.asbt.digid.common.models.dto.RequestMonitorDTO;
import uz.asbt.digid.common.models.entity.RequestMonitor;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MonitorRequestRepository extends JpaRepository<RequestMonitor, Long> {

     @Query(value = "select new uz.asbt.digid.common.models.dto.RequestMonitorDTO(rm.id, rm.guid, rm.serialNumber, rm.requestDate, rm.documentNumber, rm.documentType) " +
      "from RequestMonitor rm where (rm.requestDate between ?1 and ?2) and rm.serialNumber in (?3)")
    List<RequestMonitorDTO> findByRequestDateBetweenAndSerialNumberIn(LocalDateTime start, LocalDateTime end, String serials);

    @Query(value = "SELECT * FROM  TB_NEW_MONITOR_REQUEST t1  " +
      "WHERE t1.REQUEST_DATE = (SELECT max(REQUEST_DATE) FROM " +
      "TB_NEW_MONITOR_REQUEST t2 WHERE t1.PINPP = t2.PINPP) AND t1.PINPP = ?1", nativeQuery = true)
    RequestMonitor findLastRequestByPinpp(String pinpp);

    @Query(value = "select new uz.asbt.digid.common.models.dto.PersonDocReadData(e.documentNumber, e.requestDate, o.name) from RequestMonitor e " +
      "join Device d on e.serialNumber = d.serialNumber " +
      "join Organization o on o.id = d.organization.id where e.documentNumber = ?1")
    Page<PersonDocReadData> findAllByDocumentNumber(String documentNumber, Pageable pageable);

    RequestMonitor findTopByDocumentNumberAndPhotoNotNullOrderByRequestDateDesc(String serialNumber);
}
