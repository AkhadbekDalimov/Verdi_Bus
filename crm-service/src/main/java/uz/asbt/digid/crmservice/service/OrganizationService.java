package uz.asbt.digid.crmservice.service;

import org.springframework.data.domain.Pageable;
import uz.asbt.digid.common.models.dto.OrganizationDTO;
import uz.asbt.digid.common.models.rest.GridResponse;

import java.util.List;

public interface OrganizationService {

    OrganizationDTO save(OrganizationDTO organization);
    OrganizationDTO findById(Long id);
    GridResponse<List<OrganizationDTO>> findAll(Pageable pageable);
    OrganizationDTO findByOrgCrmId(String crmOrgId);

}
