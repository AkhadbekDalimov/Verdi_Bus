package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.dto.PersonAddressDTO;

import java.util.List;

public interface PersonAddressService {

  PersonAddressDTO savePersonAddress(PersonAddressDTO personAddressDTO);

  List<PersonAddressDTO> findAllByDocSerialNumber(String serialNumber);
}
