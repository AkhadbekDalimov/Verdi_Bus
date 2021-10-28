package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.dto.PassportDataDTO;

import java.util.List;
import java.util.Optional;

public interface PassportDataService {

  PassportDataDTO savePassportData(PassportDataDTO passportDataDTO);

  List<PassportDataDTO> findPassportDatasByPinpp(String pinpp);

  Optional<PassportDataDTO> findPassportBySerialNumber(String serialNumber);
}
