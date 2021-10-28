package uz.asbt.digid.crmservice.service.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.common.models.dto.OrganizationDTO;
import uz.asbt.digid.common.models.entity.Organization;
import uz.asbt.digid.common.models.rest.GridResponse;
import uz.asbt.digid.crmservice.exception.OrganizationSaveException;
import uz.asbt.digid.crmservice.repository.OrganizationRepository;
import uz.asbt.digid.crmservice.service.OrganizationService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

  OrganizationRepository repository;
  ModelMapper modelMapper;

  @Override
  public OrganizationDTO save(@NonNull final OrganizationDTO organization) {
    return Optional.of(organization)
      .map(org -> {
        Optional.ofNullable(repository.findByOrgCrmId(org.getOrgCrmId()))
          .ifPresent(obj -> org.setId(obj.getId()));
        return org;
      })
      .map(org -> modelMapper.map(org, Organization.class))
      .map(repository::save)
      .map(org -> modelMapper.map(org, OrganizationDTO.class))
      .orElseThrow(OrganizationSaveException::new);
  }

  @Override
  public OrganizationDTO findById(@NonNull final Long id) {
    return Optional.of(id)
      .map(repository::findById)
      .map(org -> modelMapper.map(org, OrganizationDTO.class))
      .orElseThrow(EntityFindException::new);
  }

  @Override
  public OrganizationDTO findByOrgCrmId(@NonNull final String crmOrgId) {
    return Optional.of(crmOrgId)
      .map(repository::findByOrgCrmId)
      .map(org -> modelMapper.map(org, OrganizationDTO.class))
      .orElseThrow(EntityFindException::new);
  }

  @Override
  public GridResponse<List<OrganizationDTO>> findAll(@NonNull final Pageable pageable) {
    return Optional.of(pageable)
      .map(repository::findAll)
      .map(orgs -> GridResponse
        .<List<OrganizationDTO>>builder()
        .list(orgs.getContent().stream().map(i -> modelMapper.map(i, OrganizationDTO.class)).collect(toList()))
        .totalItemsCount(orgs.getTotalElements())
        .totalPages(orgs.getTotalPages())
        .build())
      .orElseThrow(EntityFindException::new);
  }
}
