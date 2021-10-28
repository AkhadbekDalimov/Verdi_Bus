package uz.asbt.digid.common.service.monitor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.dto.LivenessLoggerRequestDTO;
import uz.asbt.digid.common.models.dto.LivenessLoggerResponseDTO;
import uz.asbt.digid.common.models.entity.LivenessLoggerRequest;
import uz.asbt.digid.common.models.entity.LivenessLoggerResponse;
import uz.asbt.digid.common.repository.LivenessRequestRepository;
import uz.asbt.digid.common.repository.LivenessResponseRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service("livenessMonitorService")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LivenessMonitor implements ILivenessReport<LivenessLoggerRequest, LivenessLoggerResponse> {

  LivenessRequestRepository requestRepository;
  LivenessResponseRepository responseRepository;

  @Async
  @Override
  public void saveRequest(LivenessLoggerRequest request) {
    LivenessLoggerRequest result = requestRepository.save(request);
    if (0 == result.getId())
      log.error("Не удалось сохранить запрос {}", request);
  }

  @Async
  @Override
  public void saveResponse(LivenessLoggerResponse response) {
    LivenessLoggerResponse result = responseRepository.save(response);
    if (0 == result.getId())
      log.error("Не удалось сохранить запрос {}", response);
  }

  @Override
  public List<LivenessLoggerRequestDTO> findAllBySerialNumberAndRequestDateBetween(List<String> serials, LocalDateTime start, LocalDateTime end) {
    return requestRepository.findByRequestDateBetweenAndSerialNumberIn(start, end, serials);
  }

  @Override
  public List<LivenessLoggerResponseDTO> findAllByGuid(List<String> guids) {
    return responseRepository.findByGuidIn(guids);
  }
}
