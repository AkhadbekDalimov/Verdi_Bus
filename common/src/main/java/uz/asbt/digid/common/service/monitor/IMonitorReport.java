package uz.asbt.digid.common.service.monitor;

import org.springframework.data.domain.Pageable;
import uz.asbt.digid.common.models.dto.PersonDocReadData;
import uz.asbt.digid.common.models.dto.RequestMonitorDTO;
import uz.asbt.digid.common.models.dto.ResponseMonitorDTO;
import uz.asbt.digid.common.models.rest.GridResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IMonitorReport<T, R> extends IMonitor<T, R>, IReport {

  @Override
  void saveRequest(T request);

  @Override
  void saveResponse(R response);

  @Override
  List<RequestMonitorDTO> findAllBySerialNumberAndRequestDateBetween(String serials, LocalDateTime start, LocalDateTime end);

  @Override
  List<ResponseMonitorDTO> findAllByGuid(List<String> guids);

  @Override
  RequestMonitorDTO findLastRequestMonitorByPinpp(String pinpp);

  @Override
  GridResponse<List<PersonDocReadData>> findAllByDocumentNumber(Pageable pageable, String documentNumber);
}
