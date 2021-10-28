package uz.asbt.digid.common.service.monitor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.common.models.dto.PersonDocReadData;
import uz.asbt.digid.common.models.dto.RequestMonitorDTO;
import uz.asbt.digid.common.models.dto.ResponseMonitorDTO;
import uz.asbt.digid.common.models.entity.RequestMonitor;
import uz.asbt.digid.common.models.entity.ResponseMonitor;
import uz.asbt.digid.common.models.rest.GridResponse;
import uz.asbt.digid.common.repository.MonitorRequestRepository;
import uz.asbt.digid.common.repository.MonitorResponseRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("monitorService")
@RequiredArgsConstructor
public class MonitorService implements IMonitorReport<RequestMonitor, ResponseMonitor> {

    private final MonitorRequestRepository requestRepository;
    private final MonitorResponseRepository responseRepository;
    private final ModelMapper modelMapper;

    @Async
    @Override
    public void saveRequest(RequestMonitor request) {

        RequestMonitor result = requestRepository.save(request);
        if (0 == result.getId())
            log.error("Не удалось сохранить запрос {}", request);
    }

    @Async
    @Override
    public void saveResponse(ResponseMonitor response) {
        ResponseMonitor result = responseRepository.save(response);
        if (0 == result.getId())
            log.error("Не удалось сохранить запрос {}", response);
    }

    @Override
    public List<RequestMonitorDTO> findAllBySerialNumberAndRequestDateBetween(String serials, LocalDateTime start, LocalDateTime end) {
        return requestRepository.findByRequestDateBetweenAndSerialNumberIn(start, end, serials);
    }

    @Override
    public List<ResponseMonitorDTO> findAllByGuid(List<String> guids) {
        return responseRepository.findByGuidIn(guids);
    }

    @Override
    public RequestMonitorDTO findLastRequestMonitorByPinpp(String pinpp) {
        return Optional.ofNullable(pinpp)
          .map(p -> {
              final RequestMonitor requestMonitorDTO = requestRepository.findLastRequestByPinpp(pinpp);
              log.info("request monitor {}", requestMonitorDTO);
              return requestMonitorDTO;
          })
          .map(p -> modelMapper.map(p, RequestMonitorDTO.class))
          .orElse(null);
    }

    @Override
    public GridResponse<List<PersonDocReadData>> findAllByDocumentNumber(@NonNull final Pageable pageable, final String documentNumber) {
        return Optional.of(pageable)
          .flatMap(p -> Optional.ofNullable(requestRepository.findAllByDocumentNumber(documentNumber, pageable)))
          .map(monitors -> GridResponse
            .<List<PersonDocReadData>>builder()
            .list(monitors.getContent())
            .totalItemsCount(monitors.getTotalElements())
            .totalPages(monitors.getTotalPages())
            .build())
          .orElseThrow(EntityFindException::new);
    }
}
