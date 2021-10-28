package uz.asbt.digid.digidservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@Table(name = "PERSON_ADDRESS")
public class PersonAddress implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ADDRESS_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_address_seq")
  @SequenceGenerator(name = "person_address_seq", sequenceName = "SQ_PERSON_ADDRESS", allocationSize = 1)
  private Long id;

  @Column(name = "Kadastr")
  private String kadastr;

  @Column(name = "Country")
  private long country;

  @Column(name = "CountryICAO")
  private String countryICAO;

  @Column(name = "CountryName")
  private String countryName;

  @Column(name = "CountryNameUz")
  private String countryNameUz;

  @Column(name = "Region")
  private long region;

  @Column(name = "RegionName")
  private String regionName;

  @Column(name = "RegionNameUz")
  private String regionNameUz;

  @Column(name = "District")
  private long district;

  @Column(name = "DistrictName")
  private String districtName;

  @Column(name = "DistrictNameUz")
  private String districtNameUz;

  @Column(name = "Address")
  private String address;

  @Column(name = "House")
  private String house;

  @Column(name = "Flat")
  private String flat;

  @Column(name = "Block")
  private String block;

  @Column(name = "LiveFromDate")
  private String liveFromDate;

  @Column(name = "Additional")
  private String additional;

  @Lazy
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PASSPORT_DATA_ID")
  private PassportData passportData;
}
