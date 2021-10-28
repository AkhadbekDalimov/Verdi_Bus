package uz.asbt.digid.digidservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MOBILE_DATA")
public class MobileData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "MOBILE_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mob_data_seq")
  @SequenceGenerator(name = "mob_data_seq", sequenceName = "SQ_MOBILE_DATA", allocationSize = 1)
  private Long id;

  @Column(name = "MOBILE_TYPE")
  private String mobileType;

  @Column(name = "MOBILE_NUMBER")
  private String mobileNumber;

  @Column(name = "MOBILE_DEVICE_ID")
  private String mobileDeviceId;

  @Lazy
  @ManyToOne
  @JoinColumn(name = "PINPP_ID")
  private Pinpp pinpp;
}
