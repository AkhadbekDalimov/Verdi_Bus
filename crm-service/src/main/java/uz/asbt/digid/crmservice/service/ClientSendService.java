package uz.asbt.digid.crmservice.service;

import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.entity.ClientSended;
import uz.asbt.digid.common.enums.NeedSend;

import java.util.List;

public interface ClientSendService {

    ClientSended save(ClientDTO client, int status, NeedSend needSend);
    List<ClientSended> findNotSend();
    void updateStatus(ClientSended sended);

}
