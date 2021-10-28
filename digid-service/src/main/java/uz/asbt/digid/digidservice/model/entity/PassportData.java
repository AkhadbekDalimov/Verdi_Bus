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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PASSPORT_DATA")
//@ToString(exclude = {"address"})
//@EqualsAndHashCode(exclude = {"address"})
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "address"})
public class PassportData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "PASSPORT_DATA_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passport_data_seq")
  @SequenceGenerator(name = "passport_data_seq", sequenceName = "SQ_PASSPORT_DATA", allocationSize = 1)
  private Long id;

  @Column(name = "SurnameC")
  private String surnameC;

  @Column(name = "NameC")
  private String nameC;

  @Column(name = "PatronymC")
  private String patronymC;

  @Column(name = "SurnameL")
  private String surnameL;

  @Column(name = "NameL")
  private String nameL;

  @Column(name = "PatronymL")
  private String patronymL;

  @Column(name = "SurnameE")
  private String surnameE;

  @Column(name = "NameE")
  private String nameE;

  @Column(name = "BirthDate")
  private String birthDate;

  @Column(name = "Sex")
  private String sex;

  @Column(name = "SexName")
  private String sexName;

  @Column(name = "SexNameUz")
  private String sexNameUz;

  @Column(name = "BirthCountry")
  private String birthCountry;

  @Column(name = "BirthCountryName")
  private String birthCountryName;

  @Column(name = "BirthCountryNameUz")
  private String birthCountryNameUz;

  @Column(name = "BirthPlace")
  private String birthPlace;

  @Column(name = "Nationality")
  private String nationality;

  @Column(name = "NationalityName")
  private String nationalityName;

  @Column(name = "NationalityNameUz")
  private String nationalityNameUz;

  @Column(name = "DocumentType")
  private String documentType;

  @Column(name = "DocumentTypeName")
  private String documentTypeName;

  @Column(name = "DocumentTypeNameUz")
  private String documentTypeUz;

  @Column(name = "DocumentSerialNumber")
  private String documentSerialNumber;

  @Column(name = "DocumentDateIssue")
  private String documentDateIssue;

  @Column(name = "DocumentDateValid")
  private String documentDateValid;

  @Column(name = "DocumentIssuedBy")
  private String documentIssuedBy;

  @Column(name = "PersonStatus")
  private int personStatus;

  @Column(name = "PersonStatusValue")
  private String personStatusValue;

  @Column(name = "Citizenship")
  private String citizenship;

  @Column(name = "CitizenshipName")
  private String citizenshipName;

  @Column(name = "CitizenshipNameUz")
  private String citizenshipNameUz;

  @Lazy
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PINPP_ID")
  private Pinpp pinpp;

  @OneToMany(mappedBy = "passportData")
  private List<PersonAddress> address;

}
