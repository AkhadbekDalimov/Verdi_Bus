package uz.asbt.digid.crmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.common.models.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Number> {

    Organization findByOrgCrmId(String crmId);

}
