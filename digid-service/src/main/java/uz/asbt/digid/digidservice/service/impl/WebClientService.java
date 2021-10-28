package uz.asbt.digid.digidservice.service.impl;

import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.digidservice.model.dto.WebClientRequestDTO;
import uz.asbt.digid.digidservice.service.IWebClientService;

@Service
public class WebClientService implements IWebClientService {

  @Override
  public int checkClientBlockStatus(WebClientRequestDTO clientRequest) {
    //TODO check existence of client by phone number
    return 0;
  }

  @Override
  public ModelPersonAnswere getClientDataByPhoneNumber(WebClientRequestDTO clientRequest) {
    //TODO check photo validation and return client by phone number
    return null;
  }
}
