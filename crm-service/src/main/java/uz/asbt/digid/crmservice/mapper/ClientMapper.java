package uz.asbt.digid.crmservice.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.common.models.dto.CrmMobileRegResponse;
import uz.asbt.digid.common.models.dto.OrganizationDTO;
import uz.asbt.digid.common.models.entity.Client;

import javax.annotation.PostConstruct;
import java.util.Optional;

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

//    modelMapper.createTypeMap(Device.class, DeviceDTO.class)
//      .addMappings(m -> {
//        m.using(parseToBoolean()).map(Device::getLivenessInt, DeviceDTO::setLiveness);
//      });
//
//    modelMapper.createTypeMap(DeviceDTO.class, Device.class)
//      .addMappings(m -> {
//        m.using(parseToInteger()).map(DeviceDTO::getLiveness, Device::setLivenessInt);
//      });

    modelMapper.createTypeMap(CrmMobileRegResponse.class, OrganizationDTO.class)
      .addMappings(m -> {
        m.map(CrmMobileRegResponse::getName, OrganizationDTO::setName);
        m.map(CrmMobileRegResponse::getMobileClientCrmId, OrganizationDTO::setOrgCrmId);
      });

  }

  private Converter<Boolean, Integer> parseToInteger() {
    return ctx -> Optional.ofNullable(ctx)
      .map(MappingContext::getSource)
      .map(res -> res ? 1 : 0)
      .orElseThrow(IllegalArgumentException::new);
  }

  private Converter<Integer, Boolean> parseToBoolean() {
    return ctx -> Optional.ofNullable(ctx)
      .map(MappingContext::getSource)
      .map(res -> res == 1)
      .orElseThrow(IllegalArgumentException::new);
  }

}
