package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.digidservice.model.dto.WebClientRequestDTO;

public interface IWebClientService {

  int checkClientBlockStatus(WebClientRequestDTO clientRequest);

  ModelPersonAnswere getClientDataByPhoneNumber(WebClientRequestDTO clientRequest);

}
