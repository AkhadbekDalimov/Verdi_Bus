package uz.asbt.digid.common.service.monitor;

import uz.asbt.digid.common.models.dto.LivenessLoggerRequestDTO;
import uz.asbt.digid.common.models.dto.LivenessLoggerResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ILivenessReport<T, R> extends ILiveness<T, R>{

    @Override
    void saveRequest(T request);

    @Override
    void saveResponse(R response);

    List<LivenessLoggerRequestDTO> findAllBySerialNumberAndRequestDateBetween(List<String> serials, LocalDateTime start, LocalDateTime end);

    List<LivenessLoggerResponseDTO> findAllByGuid(List<String> guids);
}
