package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.exception.DataSaveException;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.digidservice.model.dto.PinppDTO;
import uz.asbt.digid.digidservice.model.entity.Pinpp;
import uz.asbt.digid.digidservice.repository.PinppRepository;
import uz.asbt.digid.digidservice.service.PinppService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PinppServiceImpl implements PinppService {

  private final PinppRepository pinppRepository;
  private final ModelMapper modelMapper;

  @Override
  public PinppDTO savePinnp(final String pinpp) {
    final Pinpp pinppEntity = Pinpp.builder().pinpp(pinpp).build();
    return Optional.ofNullable(pinppEntity)
      .map(p -> {
          Optional.ofNullable(pinppRepository.findByPinpp(p.getPinpp()))
            .ifPresent(res -> {
              p.setId(res.getId());
            });
          return p;
        }
      )
      .map(pinppRepository::save)
      .map(org -> modelMapper.map(org, PinppDTO.class))
      .orElseThrow(DataSaveException::new);
  }

  @Override
  public PinppDTO findById(final Long id) {
    return Optional.of(id)
      .map(pinppRepository::findById)
      .map(p -> modelMapper.map(p, PinppDTO.class))
      .orElseThrow(EntityFindException::new);
  }

  @Override
  public PinppDTO findByPinpp(final String pinpp) {
    return Optional.of(pinpp)
      .map(pinppRepository::findByPinpp)
      .map(p -> modelMapper.map(p, PinppDTO.class))
      .orElseThrow(EntityFindException::new);
  }
}
