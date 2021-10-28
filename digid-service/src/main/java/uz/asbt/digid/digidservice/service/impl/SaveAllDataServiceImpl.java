package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.digidservice.model.dto.InnDTO;
import uz.asbt.digid.digidservice.model.dto.PassportDataDTO;
import uz.asbt.digid.digidservice.model.dto.PersonAddressDTO;
import uz.asbt.digid.digidservice.model.dto.PhotoDataDTO;
import uz.asbt.digid.digidservice.model.dto.PinppDTO;
import uz.asbt.digid.digidservice.service.DocReadDataService;
import uz.asbt.digid.digidservice.service.InnService;
import uz.asbt.digid.digidservice.service.PassportDataService;
import uz.asbt.digid.digidservice.service.PersonAddressService;
import uz.asbt.digid.digidservice.service.PhotoDataService;
import uz.asbt.digid.digidservice.service.PinppService;
import uz.asbt.digid.digidservice.service.SaveAllDataService;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaveAllDataServiceImpl implements SaveAllDataService {

  private final PinppService pinppService;
  private final PassportDataService passportDataService;
  private final PhotoDataService photoDataService;
  private final DocReadDataService docReadDataService;
  private final ModelMapper modelMapper;
  private final PersonAddressService personAddressService;
  private final CrmService crmService;
  private final InnService innService;
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

  @Override
  public void saveAllDataToDB(final ModelPersonAnswere person, final Locale locale) {
    try {

      log.info("Begin saving pinpp {}", person.getPerson().getPinpp());
      final PinppDTO pinppDTO = pinppService.savePinnp(person.getPerson().getPinpp());

      log.info("Begin saving passport data");
      final PassportDataDTO savedPassportDataDTO = savePassportData(person, pinppDTO);

      log.info("Begin saving photo data{}", savedPassportDataDTO);
      savePhotoData(person, savedPassportDataDTO);

      if (person.getAddress().getAnswere().getAnswereComment().equals("OK")) {
        savePersonAddress(person, savedPassportDataDTO);
      }

      log.info("Begin saving inn");
      final InnDTO innDTO = InnDTO.builder().inn(person.getAdditional().getInn()).innDate(person.getAdditional().getInnDate())
        .taxCode(person.getAdditional().getTaxCode()).build();

      innDTO.setPinpp(pinppDTO);
      saveInn(innDTO);

    } catch (final Exception e) {
      e.printStackTrace();
      log.error("error{}", e.getCause() + e.getLocalizedMessage());
    }
  }


  private InnDTO saveInn(final InnDTO innDTO) {
    log.info("inn {}", innDTO);
    return innService.save(innDTO);
  }

  private void savePersonAddress(final ModelPersonAnswere person, final PassportDataDTO savedPassportDataDTO) {
    log.info("Begin saving address data");
    final PersonAddressDTO personAddressDTO = modelMapper.map(person.getAddress().getModelAddress(), PersonAddressDTO.class);
    personAddressDTO.setPassportData(savedPassportDataDTO);
    log.info("person address {}", personAddressDTO);
    personAddressService.savePersonAddress(personAddressDTO);
  }

  private PassportDataDTO savePassportData(final ModelPersonAnswere person, final PinppDTO pinppDTO) {
    final PassportDataDTO passportDataDTO = modelMapper.map(person.getPerson(), PassportDataDTO.class);
    passportDataDTO.setPinpp(pinppDTO);
    return passportDataService.savePassportData(passportDataDTO);
  }

  private void savePhotoData(final ModelPersonAnswere person, final PassportDataDTO savedPassportDataDTO) {
    log.info("save photo{}", savedPassportDataDTO);
    final PhotoDataDTO photoData = PhotoDataDTO
      .builder()
      .passportData(savedPassportDataDTO)
      .photo(person.getModelPersonPhoto().getPersonPhoto())
      .build();

    log.info("saving photo data {}", photoData);
    photoDataService.savePhotoData(photoData);
  }
}

