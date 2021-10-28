package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.exception.DataSaveException;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.digidservice.model.dto.DocReadDataDTO;
import uz.asbt.digid.digidservice.model.entity.DocReadData;
import uz.asbt.digid.digidservice.repository.DocReadDataRepository;
import uz.asbt.digid.digidservice.service.DocReadDataService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DocReadDataServiceImpl implements DocReadDataService {

  private final DocReadDataRepository docReadDataRepository;
  private final ModelMapper  modelMapper;

  @Override
  public List<DocReadDataDTO> findAllByPinpp(final String pinpp) {
    return  Optional.of(pinpp)
      .map(docReadDataRepository::findAllByPinpp)
      .map(doc -> doc
        .stream()
        .map(d -> modelMapper.map(d, DocReadDataDTO.class)).collect(toList()))
      .orElseThrow(EntityFindException::new);
  }

  @Override
  public DocReadDataDTO saveDocReadData(final DocReadDataDTO docReadDataDTO) {
    return Optional.of(docReadDataDTO)
      .map(doc -> modelMapper.map(doc, DocReadData.class))
      .map(docReadDataRepository::save)
      .map(doc -> modelMapper.map(doc, DocReadDataDTO.class))
      .orElseThrow(DataSaveException::new);
  }
}
