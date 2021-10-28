package uz.asbt.digid.crmservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.entity.Client;
import uz.asbt.digid.common.models.entity.ClientSended;
import uz.asbt.digid.common.enums.NeedSend;
import uz.asbt.digid.crmservice.exception.ClientSaveException;
import uz.asbt.digid.crmservice.repository.ClientSendRepository;
import uz.asbt.digid.crmservice.service.ClientSendService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientSendServiceImpl implements ClientSendService {

  ClientSendRepository repository;
  ModelMapper modelMapper;

  @Override
  public ClientSended save(final ClientDTO client, final int status, final NeedSend needSend) {
    return Optional.ofNullable(client)
      .map(c -> ClientSended
        .builder()
        .client(modelMapper.map(c, Client.class))
        .needSend(needSend.getCode())
        .status(status)
        .build())
      .map(repository::save)
      .orElseThrow(ClientSaveException::new);
  }

  @Override
  public List<ClientSended> findNotSend() {
    return repository.findNotSend();
  }

  @Override
  public void updateStatus(final ClientSended sended) {
    sended.setStatus(NeedSend.NOT_SEND.getCode());
    repository.save(sended);
  }
}
