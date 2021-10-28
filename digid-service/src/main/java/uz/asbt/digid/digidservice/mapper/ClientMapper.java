package uz.asbt.digid.digidservice.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.entity.Client;

import javax.annotation.PostConstruct;

import static org.modelmapper.Conditions.isNotNull;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientMapper {

  ModelMapper modelMapper;

  @PostConstruct
  public void init() {
    modelMapper.createTypeMap(Client.class, ClientDTO.class)
      .addMappings(m -> {
        m.when(isNotNull()).map(Client::getDevice, ClientDTO::setDevice);
      });
    modelMapper.createTypeMap(ClientDTO.class, Client.class)
      .addMappings(m -> {
        m.when(isNotNull()).map(ClientDTO::getDevice, Client::setDevice);
      });
  }

}
