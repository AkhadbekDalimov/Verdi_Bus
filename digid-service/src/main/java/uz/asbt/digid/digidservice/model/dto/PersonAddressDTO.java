package uz.asbt.digid.digidservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonAddressDTO {

  private Long id;

  private String kadastr;

  private long country;

  private String countryICAO;

  private String countryName;

  private String countryNameUz;

  private long region;

  private String regionName;

  private String regionNameUz;

  private long district;

  private String districtName;

  private String districtNameUz;

  private String address;

  private String house;

  private String flat;

  private String block;

  private String liveFromDate;

  private String additional;

  @JsonIgnore
  private PassportDataDTO passportData;
}
