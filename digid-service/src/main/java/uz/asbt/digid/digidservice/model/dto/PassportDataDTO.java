package uz.asbt.digid.digidservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassportDataDTO {

  private Long id;

  private String surnameC;

  private String nameC;

  private String patronymC;

  private String surnameL;

  private String nameL;

  private String patronymL;

  private String surnameE;

  private String nameE;

  private String birthDate;

  private String sex;

  private String sexName;

  private String sexNameUz;

  private String birthCountry;

  private String birthCountryName;

  private String birthCountryNameUz;

  private String birthPlace;

  private String nationality;

  private String nationalityName;

  private String nationalityNameUz;

  private String documentType;

  private String documentTypeName;

  private String documentTypeUz;

  private String documentSerialNumber;

  private String documentDateIssue;

  private String documentDateValid;

  private String documentIssuedBy;

  private int personStatus;

  private String personStatusValue;

  private String citizenship;

  private String citizenshipName;

  private String citizenshipNameUz;

  private PinppDTO pinpp;

  private List<PersonAddressDTO> address;

  private PhotoDataDTO photoData;

  List<DocReadDataDTO> docReadData;

}
