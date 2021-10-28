package uz.asbt.digid.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.asbt.digid.common.models.dto.LivenessLoggerResponseDTO;
import uz.asbt.digid.common.models.dto.ResponseMonitorDTO;
import uz.asbt.digid.common.models.entity.LivenessLoggerResponse;

import java.util.List;

public interface LivenessResponseRepository extends JpaRepository<LivenessLoggerResponse, Long> {

  @Query(value = "select new uz.asbt.digid.common.models.dto.LivenessLoggerResponseDTO(rm.id, rm.guid, rm.code, rm.message, rm.responseDate) " +
          "from LivenessLoggerResponse rm where rm.guid in ?1")
  List<LivenessLoggerResponseDTO> findByGuidIn(List<String> guids);

}
