package uz.asbt.digid.crmservice.service;

import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.CrmMobileRegRequest;

public interface MobileClientService {

  ClientDTO sendMobileClientDataToCrm(CrmMobileRegRequest crmMobileRegRequest);
}
