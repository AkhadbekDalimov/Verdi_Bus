package uz.asbt.digid.crmservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.dto.DeviceDTO;
import uz.asbt.digid.common.models.dto.LivenessLoggerRequestDTO;
import uz.asbt.digid.common.models.dto.LivenessLoggerResponseDTO;
import uz.asbt.digid.common.models.dto.RequestMonitorDTO;
import uz.asbt.digid.common.models.dto.ResponseMonitorDTO;
import uz.asbt.digid.common.models.entity.LivenessLoggerRequest;
import uz.asbt.digid.common.models.entity.LivenessLoggerResponse;
import uz.asbt.digid.common.service.monitor.ILivenessReport;
import uz.asbt.digid.common.service.monitor.IMonitorReport;
import uz.asbt.digid.crmservice.model.CombineLiveness;
import uz.asbt.digid.crmservice.model.CombineMonitor;
import uz.asbt.digid.crmservice.model.Report;
import uz.asbt.digid.crmservice.model.ReportRequest;
import uz.asbt.digid.crmservice.model.ReportResponse;
import uz.asbt.digid.crmservice.service.DeviceService;
import uz.asbt.digid.crmservice.service.ReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final DeviceService deviceService;
  private final @Qualifier("monitorService") IMonitorReport<ReportRequest, ReportResponse> monitorLog;
  private final ILivenessReport<LivenessLoggerRequest, LivenessLoggerResponse> livenessLog;

  @Override
  public ReportResponse info(final ReportRequest request) {
    final ReportResponse reportResponse = new ReportResponse();
    reportResponse.setEndDate(request.getEndDate());
    reportResponse.setStartDate(request.getStartDate());
    reportResponse.setActivityId(request.getActivityId());
    final List<Report> reports = new ArrayList<>();
    log.info("Begin getting list of devices");
    final List<DeviceDTO> devices = deviceService.findAllByDeviceCrmId(request.getDevicesCrmId());
    log.info("End getting list of devices, count: {}", devices.size());
    if (!devices.isEmpty()) {
      log.info("Begin getting list of monitor request");
      final List<RequestMonitorDTO> requestMonitors = new ArrayList<>();
      final List<List<String>> partitions1 = ListUtils.partition(
        devices.stream().map(DeviceDTO::getSerialNumber)
          .collect(Collectors.toList()), 999);
      for (List<String> i: partitions1) {
        for (String s : i) {
          requestMonitors.addAll(monitorLog.findAllBySerialNumberAndRequestDateBetween(s, request.getStartDate(), request.getEndDate()));
        }
      }
//      partitions1.forEach(i -> requestMonitors.addAll(monitorLog.findAllBySerialNumberAndRequestDateBetween(i, request.getStartDate(), request.getEndDate())));
      log.info("End getting list of monitor request, count: {}", requestMonitors.size());
      if (!requestMonitors.isEmpty()) {
        log.info("Begin getting list of monitor responses");
        final List<ResponseMonitorDTO> responseMonitors = new ArrayList<>();
        final List<List<String>> partitions2 = ListUtils.partition(requestMonitors.stream().map(RequestMonitorDTO::getGuid).collect(Collectors.toList()), 999);
        partitions2.forEach(i -> responseMonitors.addAll(monitorLog.findAllByGuid(i)));
        log.info("End getting list of monitor responses, count: {}", responseMonitors.size());
        log.info("Begin combine requests and responses");
        final List<CombineMonitor> list = requestMonitors.stream()
          .map(i -> {
            final CombineMonitor monitor = new CombineMonitor();
            monitor.setGuid(i.getGuid());
            monitor.setSerialNumber(i.getSerialNumber());
            monitor.setRequestDate(i.getRequestDate());
            final ResponseMonitorDTO res = responseMonitors
              .stream()
              .filter(e -> e.getGuid().equals(i.getGuid()))
              .findAny()
              .orElse(null);
            if (res != null) {
              monitor.setCode(res.getCode());
              monitor.setMessage(res.getMessage());
            }
            return monitor;
          }).collect(Collectors.toList());
        log.info("End combine requests and responses");
        devices.forEach(i -> {
          final Report report = new Report();
          report.setDeviceId(i.getDeviceCrmId());
          report.setRequestsQuantity(String.valueOf(list.stream()
            .filter(req -> req.getSerialNumber().equals(i.getSerialNumber()))
            .count()));
          report.setResponceQuantity(String.valueOf(list.stream()
            .filter(res -> checkMonitor(res, i.getSerialNumber()))
            .count()));
          report.setSuccessResponceQuantity(String.valueOf(list.stream()
            .filter(res -> checkMonitor(res, i.getSerialNumber()))
            //TODO  && checkStatusChangedDate(res.getRequestDate().) need add status check with 0 date 21.04.2021
            .filter(res -> res.getCode().equals("1") || (res.getCode().equals("0"))|| res.getCode().equals("02000"))
            .count()));
          reports.add(report);
        });
        reportResponse.setDevicesCrmId(reports);
      }
    }
    log.info("Finish");
    return reportResponse;
  }

  @Override
  public ReportResponse livenessReport(final ReportRequest request) {
    final ReportResponse reportResponse = new ReportResponse();
    reportResponse.setStartDate(request.getStartDate());
    reportResponse.setEndDate(request.getEndDate());
    reportResponse.setActivityId(request.getActivityId());
    final List<Report> reports = new ArrayList<>();
    log.info("Begin getting list of devices for livenessreport");
    final List<DeviceDTO> devices = deviceService.findAllByDeviceCrmId(request.getDevicesCrmId());
    log.info("End getting list of devices for livenessreport, count: {}", devices.size());
    if(!devices.isEmpty()) {
      log.info("Begin getting list of livenesslog reuest");
      final List<LivenessLoggerRequestDTO> livenessLoggerRequests = new ArrayList<>();
      final List<List<String>> partitions1 = ListUtils.partition(
              devices.stream().map(DeviceDTO::getSerialNumber)
                      .collect(Collectors.toList()), 999);
      partitions1.forEach(i -> livenessLoggerRequests
              .addAll(livenessLog.findAllBySerialNumberAndRequestDateBetween(i, request.getStartDate(), request.getEndDate())));
      log.info("End getting list of liveness requests, count: {}", livenessLoggerRequests.size());
      if(!livenessLoggerRequests.isEmpty()){
        log.info("Begin getting list of liveness responses");
        final List<LivenessLoggerResponseDTO> livenessLoggerResponses = new ArrayList<>();
        final List<List<String>> partitions2 = ListUtils.partition(livenessLoggerRequests.stream().map(LivenessLoggerRequestDTO::getGuid)
                .collect(Collectors.toList()), 999);
        partitions2.forEach(i -> livenessLoggerResponses.addAll(livenessLog.findAllByGuid(i)));
        log.info("End getting list of liveness responses, count: {}", livenessLoggerResponses.size());
        log.info("begin combine liveness requests and responses");
        final List<CombineLiveness> combineLivenesses = livenessLoggerRequests.stream()
                .map(i -> {
                  final CombineLiveness liveness = new CombineLiveness();
                  liveness.setGuid(i.getGuid());
                  liveness.setSerialNumber(i.getSerialNumber());
                  liveness.setRequestDate(i.getRequestDate());
                  final LivenessLoggerResponseDTO res = livenessLoggerResponses
                          .stream()
                          .filter(e -> e.getGuid().equals(i.getGuid()))
                          .findAny()
                          .orElse(null);
                  if(res != null) {
                    liveness.setCode(res.getCode());
                    liveness.setMessage(res.getMessage());
                  }
                  return liveness;
                }).collect(Collectors.toList());
        log.info("End combine liveness requests and responses");
        devices.forEach(i -> {
          final Report report = new Report();
          report.setDeviceId(i.getDeviceCrmId());
          report.setRequestsQuantity(String.valueOf(combineLivenesses.stream()
                  .filter(req -> req.getSerialNumber().equals(i.getSerialNumber()))
                  .count()));
          report.setResponceQuantity(String.valueOf(combineLivenesses.stream()
                  .filter(res -> checkLiveness(res, i.getSerialNumber()))
                  .count()));
          report.setSuccessResponceQuantity(String.valueOf(combineLivenesses.stream()
                  .filter(res -> checkLiveness(res, i.getSerialNumber()))
                  .filter(res -> res.getCode().equals("0")) //TODO Посмотреть коды
          ));
        });
        reportResponse.setDevicesCrmId(reports);
      }
    }
    log.info("Finish liveness report");
    return reportResponse;
  }

  private boolean checkMonitor(final CombineMonitor monitor, final String serialNumber) {
    return monitor.getSerialNumber().equals(serialNumber) && monitor.getCode() != null && !monitor.getCode().equals("");
  }

  private boolean checkLiveness(final CombineLiveness monitor, final String serialNumber) {
    return monitor.getSerialNumber().equals(serialNumber) && monitor.getCode() != null && !monitor.getCode().equals("");
  }
}
