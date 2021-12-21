package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.common.models.dto.MobileAppIdRequestDTO;
import uz.asbt.digid.digidservice.model.dto.*;

import java.util.List;

public interface MobileDataService {

  MobileDataDTO saveMobileDataDTO(MobileDataDTO mobileDataDTO);

  MobileDataDTO findMobileDataByMobileInfo(MobileDataDTO mobileDataDTO);

  List<MobileDataDTO> findAllByPinpp(String pinpp);

  MobileAppIdResponseDTO findAllByAppId(MobileAppIdRequestDTO requestDTO);

  void deleteById(Long id);

  void deleteByMobileInfo(MobileDataDTO mobileDataDTO);

  MobileResponseGuidDTO sendGuid(String appUrl, MobileAppIdRequestDTO mobileAppIdRequestDTO);
}
