package uz.asbt.digid.digidservice.service;

import uz.asbt.digid.digidservice.model.dto.PhotoDataDTO;

import java.util.Optional;

public interface PhotoDataService {

  PhotoDataDTO savePhotoData(PhotoDataDTO photoDataDTO);

  PhotoDataDTO findPhotoDataByPassportDataId(Long id);

  Optional<PhotoDataDTO> findPhotoDataByPassportSerialNumber(String documentNumber);
}
