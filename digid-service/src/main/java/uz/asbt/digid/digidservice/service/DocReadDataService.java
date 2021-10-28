package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.dto.DocReadDataDTO;

import java.util.List;

public interface DocReadDataService {

  List<DocReadDataDTO> findAllByPinpp(String pinpp);

  DocReadDataDTO saveDocReadData(DocReadDataDTO docReadDataDTO);
}
