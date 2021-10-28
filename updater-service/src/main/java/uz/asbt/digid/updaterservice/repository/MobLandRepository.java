package uz.asbt.digid.updaterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.updaterservice.model.MobileLangVersion;
import uz.asbt.digid.updaterservice.model.Update;

import java.util.Optional;

@Repository
public interface MobLandRepository extends JpaRepository<MobileLangVersion, Long> {

  @Query(value = "select * from (select u.* from TB_MOBILE_LANG_VERSION u " +
    "order by to_number(replace(u.version, '.', '')) desc)  " +
    "where rownum = 1", nativeQuery = true)
  MobileLangVersion findLatestUpdate();
}
