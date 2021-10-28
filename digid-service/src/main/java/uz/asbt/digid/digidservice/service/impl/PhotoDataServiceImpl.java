package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.exception.DataSaveException;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.common.repository.MonitorRequestRepository;
import uz.asbt.digid.digidservice.model.dto.PhotoDataDTO;
import uz.asbt.digid.digidservice.model.entity.PhotoData;
import uz.asbt.digid.digidservice.repository.PhotoDataRepository;
import uz.asbt.digid.digidservice.service.PhotoDataService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoDataServiceImpl implements PhotoDataService {

  private final PhotoDataRepository photoDataRepository;
  private final ModelMapper modelMapper;
  private final MonitorRequestRepository monitorRequestRepository;

  @Override
  public PhotoDataDTO savePhotoData(final PhotoDataDTO photoDataDTO) {
    return Optional.ofNullable(photoDataDTO)
      .map(p -> Optional.ofNullable(photoDataRepository.findByPassportDataId(p.getPassportData().getId()))
          .map(res -> {
            p.setId(res.getId());
            return p;
          }).orElse(p)
      )
      .map(p -> modelMapper.map(p, PhotoData.class))
      .map(photoDataRepository::save)
      .map(p -> modelMapper.map(p, PhotoDataDTO.class))
      .orElseThrow(DataSaveException::new);
  }

  @Override
  public PhotoDataDTO findPhotoDataByPassportDataId(final Long id) {
    return Optional.of(id)
      .map(photoDataRepository::findByPassportDataId)
      .map(p -> modelMapper.map(p, PhotoDataDTO.class))
      .orElseThrow(EntityFindException::new);
  }

  public Optional<PhotoDataDTO> findPhotoDataByPassportSerialNumber(final String documentSerialNumber) {
    return Optional.of(documentSerialNumber)
      .map(monitorRequestRepository::findTopByDocumentNumberAndPhotoNotNullOrderByRequestDateDesc)
      .map(p -> modelMapper.map(p, PhotoDataDTO.class));
  }
}
