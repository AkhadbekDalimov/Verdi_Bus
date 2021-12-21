package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.common.models.dto.MobileAppIdRequestDTO;

public interface AppBackIntegrationService {

    void sendRegDataToAppBackEnd(MobileAppIdRequestDTO appIdRequestDTO);
}
