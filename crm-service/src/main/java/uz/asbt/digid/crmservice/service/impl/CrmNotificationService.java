package uz.asbt.digid.crmservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.entity.ClientSended;
import uz.asbt.digid.common.enums.NeedSend;
import uz.asbt.digid.common.enums.SendStatus;

@RequiredArgsConstructor
@Slf4j
@Service
public class CrmNotificationService {

  private final ClientSendServiceImpl sendService;
  private final CRMIntegrationService integrationService;

  @Async
  public void sendNewStatus(final ClientDTO client, final SendStatus status) {
    try {
      final ClientSended sended = sendService.save(client, status.getStatus(), NeedSend.SEND);
      integrationService.sendToCRM(sended);
      if (null == sended)
        throw new IllegalArgumentException("Can't save send status");
    } catch (final Exception ex) {
      log.error(ex.getMessage());
    }
  }

}
