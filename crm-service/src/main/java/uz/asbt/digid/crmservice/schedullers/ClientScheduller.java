package uz.asbt.digid.crmservice.schedullers;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.entity.ClientSended;
import uz.asbt.digid.common.enums.NeedSend;
import uz.asbt.digid.common.enums.SendStatus;
import uz.asbt.digid.crmservice.exception.CrmException;
import uz.asbt.digid.crmservice.service.ClientSendService;
import uz.asbt.digid.crmservice.service.impl.CRMIntegrationService;

import java.util.List;

@Transactional
@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientScheduller {

  final ClientSendService sendService;
  final ModelMapper mapper;
  final CRMIntegrationService integrationService;

  @Scheduled(fixedDelay = 600000)
  public void update() {
    log.info("Begin scheduller");
    final List<ClientSended> notSended = sendService.findNotSend();
    log.info("Clients count not sended: {}", notSended.size());
    if (!notSended.isEmpty()) {
      try {
        for (final ClientSended c : notSended) {
          log.info("Client who send to CRM-Service: {}", c);
          try {
            integrationService.sendToCRM(c);
            final ClientSended result = sendService.save(mapper.map(c.getClient(), ClientDTO.class),
              SendStatus.SEND.getStatus(), NeedSend.NOT_SEND);
            if (null == result)
              throw new IllegalArgumentException(String.format("Can't update client id: %d", c.getClient().getId()));
            sendService.updateStatus(c);
          } catch (final CrmException ex) {
            log.error(String.format("Code: %d, Message: %s", ex.getCode(), ex.getMessage()));
          } catch (final Exception ex) {
            log.error(ex.getMessage());
          }
        }
      } catch (final RestClientException | IllegalArgumentException ex) {
        log.error(ex.getMessage());
      }
    }
  }

}
