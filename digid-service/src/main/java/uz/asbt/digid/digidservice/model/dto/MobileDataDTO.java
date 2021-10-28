package uz.asbt.digid.digidservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MobileDataDTO {

  private Long id;

  private String mobileType;

  private String mobileNumber;

  private String mobileDeviceId;

  private PinppDTO pinpp;
}
