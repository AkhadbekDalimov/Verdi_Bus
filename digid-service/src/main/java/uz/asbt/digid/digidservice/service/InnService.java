package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.dto.InnDTO;

public interface InnService {

  InnDTO save(InnDTO innDTO);

  InnDTO findByPinpp(String pinpp);
}
