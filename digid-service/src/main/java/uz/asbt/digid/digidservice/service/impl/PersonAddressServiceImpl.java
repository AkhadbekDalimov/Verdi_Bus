package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.exception.DataSaveException;
import uz.asbt.digid.digidservice.model.dto.PersonAddressDTO;
import uz.asbt.digid.digidservice.model.entity.PersonAddress;
import uz.asbt.digid.digidservice.repository.PersonAddressRepository;
import uz.asbt.digid.digidservice.service.PersonAddressService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonAddressServiceImpl implements PersonAddressService {

  private final PersonAddressRepository personAddressRepository;
  private final ModelMapper modelMapper;

  @Override
  public PersonAddressDTO savePersonAddress(final PersonAddressDTO personAddressDTO) {
    return Optional.ofNullable(personAddressDTO)
      .map(p -> Optional.ofNullable(personAddressRepository.findByDocSerialNumberAndAddress(personAddressDTO.getPassportData().getDocumentSerialNumber(),
        personAddressDTO.getAddress()))
        .map(res -> {
          p.setId(res.getId());
          return p;
        }).orElse(p)
      )
      .map(p -> modelMapper.map(p, PersonAddress.class))
      .map(personAddressRepository::save)
      .map(p -> modelMapper.map(p, PersonAddressDTO.class))
      .orElseThrow(DataSaveException::new);
  }

  @Override
  public List<PersonAddressDTO> findAllByDocSerialNumber(final String serialNumber) {
    return personAddressRepository.findAllByDocSerialNumber(serialNumber)
      .stream()
      .map(p -> modelMapper.map(p, PersonAddressDTO.class))
      .collect(Collectors.toList());
  }
}
