package uz.asbt.digid.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.asbt.digid.common.models.dto.LivenessLoggerRequestDTO;
import uz.asbt.digid.common.models.dto.RequestMonitorDTO;
import uz.asbt.digid.common.models.entity.LivenessLoggerRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface LivenessRequestRepository extends JpaRepository<LivenessLoggerRequest, Long> {

  @Query(value = "select new uz.asbt.digid.common.models.dto.LivenessLoggerRequestDTO(lq.id, lq.guid, lq.serialNumber, " +
          " lq.pinpp, lq.documentNumber, lq.requestDate) " +
          "from LivenessLoggerRequest lq where (lq.requestDate between ?1 and ?2) and lq.serialNumber in (?3)")
  List<LivenessLoggerRequestDTO> findByRequestDateBetweenAndSerialNumberIn(LocalDateTime start, LocalDateTime end, List<String> serials);

}
