package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.asbt.digid.digidservice.model.entity.Inn;

public interface InnRepository extends JpaRepository<Inn, Long> {

  @Query(value = "SELECT * FROM INN I JOIN PINPP P ON I.PINPP_ID=P.PINPP_ID WHERE P.PINPP=?1", nativeQuery = true)
  Inn findByPinpp(String pinpp);
}
