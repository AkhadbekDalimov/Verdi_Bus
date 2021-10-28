package uz.asbt.digid.digidservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.asbt.digid.digidservice.model.entity.PhoneVerification;

public interface PhoneVerificationRepository extends JpaRepository<PhoneVerification, Long> {

  boolean existsByPhoneNumberAndLocalDateTime(String phone, String currentDate);

  boolean existsByPhoneNumberAndStatus(String phone, int status);

  PhoneVerification getByPhoneNumberAndLocalDateTime(String phoneNumber, String currentDate);
}
