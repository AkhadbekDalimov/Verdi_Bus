package uz.asbt.digid.common.service.monitor;

import org.springframework.data.domain.Pageable;
import uz.asbt.digid.common.models.dto.PersonDocReadData;
import uz.asbt.digid.common.models.dto.RequestMonitorDTO;
import uz.asbt.digid.common.models.dto.ResponseMonitorDTO;
import uz.asbt.digid.common.models.rest.GridResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IReport {

  List<RequestMonitorDTO> findAllBySerialNumberAndRequestDateBetween(String serials, LocalDateTime start, LocalDateTime end);
  List<ResponseMonitorDTO> findAllByGuid(List<String> guids);

  RequestMonitorDTO findLastRequestMonitorByPinpp(String pinpp);

  GridResponse<List<PersonDocReadData>> findAllByDocumentNumber(Pageable pageable, String documentNumber);
}
