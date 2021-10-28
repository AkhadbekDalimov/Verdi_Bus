package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.exception.DataSaveException;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.digidservice.model.dto.InnDTO;
import uz.asbt.digid.digidservice.model.entity.Inn;
import uz.asbt.digid.digidservice.repository.InnRepository;
import uz.asbt.digid.digidservice.service.InnService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InnServiceImpl implements InnService {

  private InnRepository innRepository;

  private ModelMapper modelMapper;

  @Autowired
  public InnServiceImpl(final InnRepository innRepository, final ModelMapper modelMapper) {
    this.innRepository = innRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public InnDTO save(final InnDTO innDTO) {
    return Optional.of(innDTO)
          .map(p -> {
            Optional.ofNullable(innRepository.findByPinpp(innDTO.getPinpp().getPinpp()))
              .ifPresent(res -> {
                p.setId(res.getId());
                p.setInn(res.getInn());
                p.setInnDate(res.getInnDate());
              });
            return p;
      })
      .map(p -> modelMapper.map(p, Inn.class))
      .map(innRepository::save)
      .map(p -> modelMapper.map(p, InnDTO.class))
      .orElseThrow(DataSaveException::new);
  }

  @Override
  public InnDTO findByPinpp(final String pinpp) {
    return Optional.of(pinpp)
      .map(innRepository::findByPinpp)
      .map(m -> modelMapper.map(m, InnDTO.class))
      .orElseThrow(EntityFindException::new);
  }
}
