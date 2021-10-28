package uz.asbt.digid.updaterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.asbt.digid.updaterservice.model.Version;

public interface VersionRepository extends JpaRepository<Version, Long> {

  @Query(value = "select v from Version v where v.isDeleted = 0")
  Version findByVersion();

}
