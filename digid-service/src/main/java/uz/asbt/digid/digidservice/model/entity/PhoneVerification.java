package uz.asbt.digid.digidservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PHONE_VERIFICATION")
public class PhoneVerification implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_verification_seq")
  @SequenceGenerator(name = "phone_verification_seq", sequenceName = "SQ_PHONE_VERIFICATION", allocationSize = 1)
  private Long id;

  @Column(name = "PHONE_NUMBER")
  private String phoneNumber;

  @Column(name = "REQUEST_DATE")
  private String localDateTime;

  @Column(name = "REQUEST_COUNT", columnDefinition = "default int 1")
  private int count;

  @Column(name = "SMS_CODE")
  private Integer smsCode;

  @Column(name = "STATUS", columnDefinition = "default int 1")
  private int status;
}
