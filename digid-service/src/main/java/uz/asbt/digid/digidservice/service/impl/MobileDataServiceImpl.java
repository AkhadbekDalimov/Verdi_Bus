package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.asbt.digid.common.exception.CustomException;
import uz.asbt.digid.common.exception.DataSaveException;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.common.exception.IntegrationException;
import uz.asbt.digid.common.models.entity.Client;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.digidservice.model.dto.*;
import uz.asbt.digid.digidservice.model.entity.MobileData;
import uz.asbt.digid.digidservice.repository.ClientRepository;
import uz.asbt.digid.digidservice.repository.MobileDataRepository;
import uz.asbt.digid.digidservice.service.MobileDataService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class MobileDataServiceImpl implements MobileDataService {

  private final RestTemplate restTemplate;
  private final MobileDataRepository mobileDataRepository;
  private final ClientRepository clientRepository;
  private final ModelMapper modelMapper;

  @Override
  public MobileDataDTO saveMobileDataDTO(final MobileDataDTO mobileDataDTO) {
    return Optional.of(mobileDataDTO)
      .map(p -> Optional.ofNullable(mobileDataRepository
        .findByMobileDeviceId(mobileDataDTO.getMobileDeviceId()))
        .map(res -> {
          p.setId(res.getId());
          return p;
        }).orElse(p))
      .map(p -> modelMapper.map(p, MobileData.class))
      .map(mobileDataRepository::save)
      .map(p -> modelMapper.map(p, MobileDataDTO.class))
      .orElseThrow(DataSaveException::new);
  }

  @Override
  public MobileDataDTO findMobileDataByMobileInfo(final MobileDataDTO mobileDataDTO) {
   return Optional.of(mobileDataDTO.getMobileDeviceId())
     .map(mobileDataRepository::findByMobileDeviceId)
     .map(m -> modelMapper.map(m, MobileDataDTO.class))
     .orElseThrow(EntityFindException::new);
  }

  @Override
  public List<MobileDataDTO> findAllByPinpp(final String pinpp) {
    return mobileDataRepository.findAllByPinpp(pinpp)
      .stream()
      .map(m -> modelMapper.map(m, MobileDataDTO.class))
      .collect(Collectors.toList());
  }

  @Override
  public MobileAppIdResponseDTO findAllByAppId(final MobileAppIdRequestDTO requestDTO) {

    List<MobileAppUrlDTO> listAppsId = clientRepository.findAllByAppId(requestDTO.getAppId())
            .stream()
            .filter(Objects::nonNull)
            .map(d -> modelMapper.map(d, MobileAppUrlDTO.class))
            .collect(toList());

    MobileAppIdResponseDTO respnse = new MobileAppIdResponseDTO();
    if (listAppsId.isEmpty()) {
      respnse.setSuccess(false);
    } else {
      String appUrl = listAppsId.get(0).getAppUrl();
      if (!appUrl.equals("api.digid.uz"))
        respnse.setSuccess(sendGuid(appUrl, requestDTO).isSuccess());
      else
        respnse.setSuccess(true);
    }
    respnse.setReqGUID(requestDTO.getReqGUID());
    return respnse;
  }

  @Override
  public void deleteById(final Long id) {
    final Optional<MobileData> mobileDataOptional = mobileDataRepository.findById(id);
    if(mobileDataOptional.isPresent()){
      mobileDataRepository.deleteById(id);
    } else {
      throw new EntityFindException();
    }
  }

  @Override
  public void deleteByMobileInfo(final MobileDataDTO mobileDataDTO) {
    final Optional<MobileData> mobileDataOptional = Optional.ofNullable(mobileDataRepository
      .findByMobileDeviceId(mobileDataDTO.getMobileDeviceId()));
    if(mobileDataOptional.isPresent()){
      mobileDataRepository.deleteById(mobileDataOptional.get().getId());
    } else {
      throw new EntityFindException();
    }
  }

  @Override
  public MobileResponseGuidDTO sendGuid(final String appUrl, final MobileAppIdRequestDTO mobileAppIdRequestDTO) {
    MobileRequestGuidDTO request = new MobileRequestGuidDTO();
    request.setReqGUID(mobileAppIdRequestDTO.getReqGUID());
    final Response<MobileResponseGuidDTO> response;
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    final HttpEntity<MobileRequestGuidDTO> requestEntity = new HttpEntity<>(request, headers);

    try {
      final URI uri = new URI(appUrl);

      final ResponseEntity<Response<MobileResponseGuidDTO>> responseEntity = restTemplate.exchange(
              uri,
              HttpMethod.POST,
              requestEntity,
              new ParameterizedTypeReference<Response<MobileResponseGuidDTO>>() {
              });
      if (requestEntity.getBody() == null)
        throw new IntegrationException(
                IntegrationException.COMMUNICATION_ERROR,
                "Не удалось связаться с удаленным сервисом"
        );

      if (responseEntity.getStatusCode() != HttpStatus.OK) {
        throw new IntegrationException(IntegrationException.COMMUNICATION_ERROR, "Не удалось связаться с удаленным сервисом");
      }

      response = responseEntity.getBody();
      log.info("----------------------------- POST Guid Mobile Response: " + response);
      if (response.getCode() != 0) {
        throw new IntegrationException(response.getCode(), response.getMessage());
      }

    } catch (final URISyntaxException ex) {
      log.error(ex.getMessage());
      throw new IntegrationException(IntegrationException.URL_FORMAT_ERROR, ex.getMessage());
    } catch (final Exception ex) {
      throw new CustomException(IntegrationException.UNKNOWN_ERROR, ex.getMessage());
    }
    return response.getResponse();
  }
}
