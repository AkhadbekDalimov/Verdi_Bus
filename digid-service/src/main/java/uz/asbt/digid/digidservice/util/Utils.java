package uz.asbt.digid.digidservice.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.RequestMonitorDTO;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.entity.client.ModelServiceInfo;
import uz.asbt.digid.common.models.entity.client.ServiceInfo;
import uz.asbt.digid.common.service.ErrorService;
import uz.asbt.digid.common.service.monitor.IMonitorReport;
import uz.asbt.digid.digidservice.service.impl.CrmService;

import java.util.Locale;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class Utils {

  ErrorService error;
  CrmService crmService;
  IMonitorReport monitorService;

  public String getPersonPinpp(final ModelPersonAnswere personAnswere, final Locale locale) {
    log.info("getting client from crm {}", personAnswere);
    final ClientDTO clientDTO = crmService.findBySerialNumber(getPersonSerialNumber(personAnswere), locale);
    log.info("end getting client data {}", clientDTO);
    return clientDTO.getDevice().getOrganization().getPinpp();
  }

  public String getPersonDocumentNumberByRequestMonitor(final ModelPersonAnswere personAnswere, final Locale locale) {
    log.info("getting client from crm {}", personAnswere);
    final ClientDTO clientDTO = crmService.findBySerialNumber(getPersonSerialNumber(personAnswere), locale);
    log.info("getting request monitor {}", clientDTO);
    final RequestMonitorDTO requestMonitorDTO = monitorService.findLastRequestMonitorByPinpp(clientDTO.getDevice().getOrganization().getPinpp());
    log.info("request monitor {}", requestMonitorDTO);
    return requestMonitorDTO.getDocumentNumber();
  }

  public String getPersonSerialNumber(final ModelPersonAnswere personAnswere) {
    log.info("check and get serial number of mobile client {}", personAnswere);
    final Optional<ModelServiceInfo> modelServiceInfo = Optional.ofNullable(personAnswere.getModelServiceInfo());
    if (modelServiceInfo.isPresent()) {
      final ModelServiceInfo infoResult = modelServiceInfo.get();
      final Optional<ServiceInfo> serviceInfo = Optional.ofNullable(infoResult.getServiceInfo());
      if (serviceInfo.isPresent() && !serviceInfo.get().getScannerSerial().isEmpty()) {
        return serviceInfo.get().getScannerSerial();
      }
    }
    throw new CustomException(error.getErrorCode("ex300"), error.getErrorMessage("message_300"));
  }

}
