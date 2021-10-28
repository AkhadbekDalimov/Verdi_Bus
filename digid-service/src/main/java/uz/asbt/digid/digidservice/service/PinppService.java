package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.dto.PinppDTO;

public interface PinppService {

  PinppDTO savePinnp(String pinpp);
  PinppDTO findById(Long id);
  PinppDTO findByPinpp(String pinpp);
}
