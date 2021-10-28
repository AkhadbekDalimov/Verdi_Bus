package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.digidservice.model.entity.Pinpp;

@Repository
public interface PinppRepository extends JpaRepository<Pinpp, Long> {

  Pinpp findByPinpp(String pinpp);
}
