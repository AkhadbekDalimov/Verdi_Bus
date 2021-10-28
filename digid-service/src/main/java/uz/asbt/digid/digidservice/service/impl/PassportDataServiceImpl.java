package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.exception.DataSaveException;
import uz.asbt.digid.digidservice.model.dto.PassportDataDTO;
import uz.asbt.digid.digidservice.model.entity.PassportData;
import uz.asbt.digid.digidservice.repository.PassportDataRepository;
import uz.asbt.digid.digidservice.service.PassportDataService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassportDataServiceImpl implements PassportDataService {

  private final PassportDataRepository passportDataRepository;
  private final ModelMapper modelMapper;

  @Override
  public PassportDataDTO savePassportData(final PassportDataDTO passportDataDTO) {
    return Optional.ofNullable(passportDataDTO)
      .map(p -> {
        Optional.ofNullable(passportDataRepository.findByDocumentSerialNumberAndBirthDate
          (passportDataDTO.getDocumentSerialNumber(), passportDataDTO.getBirthDate()))
          .ifPresent(res -> p.setId(res.getId()));
        return p;
       })
      .map(p -> modelMapper.map(p, PassportData.class))
      .map(passportDataRepository::save)
      .map(p -> modelMapper.map(p, PassportDataDTO.class))
      .orElseThrow(DataSaveException::new);
  }

  @Override
  public List<PassportDataDTO> findPassportDatasByPinpp(final String pinpp) {
    return passportDataRepository.findPassportDataByPinpp(pinpp)
      .stream()
      .map(p -> modelMapper.map(p, PassportDataDTO.class))
      .collect(Collectors.toList());
  }

  @Override
  public Optional<PassportDataDTO> findPassportBySerialNumber(final String serialNumber) {
    return Optional.of(serialNumber)
      .map(s -> {
        final PassportData passportData = passportDataRepository.findByDocumentSerialNumber(s);
        log.info("info{}", passportData);
        return passportData;
      })
      .map(p -> modelMapper.map(p, PassportDataDTO.class));
  }
}
