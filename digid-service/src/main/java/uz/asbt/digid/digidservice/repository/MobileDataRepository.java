package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.asbt.digid.digidservice.model.entity.MobileData;

import java.util.List;

@Repository
public interface MobileDataRepository extends JpaRepository<MobileData, Long> {

  List<MobileData> findAllByPinpp(String pinpp);

  MobileData findByMobileDeviceId(String deviceId);


}
