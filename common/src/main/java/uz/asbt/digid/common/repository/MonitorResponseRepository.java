package uz.asbt.digid.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.dto.ResponseMonitorDTO;
import uz.asbt.digid.common.models.entity.ResponseMonitor;

import java.util.List;

@Repository
public interface MonitorResponseRepository extends JpaRepository<ResponseMonitor, Long> {

    @Query(value = "select new uz.asbt.digid.common.models.dto.ResponseMonitorDTO(rm.id, rm.guid, rm.code, rm.message, rm.responseDate) " +
      "from ResponseMonitor rm where rm.guid in ?1")
    List<ResponseMonitorDTO> findByGuidIn(List<String> guids);
}
