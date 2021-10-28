package uz.asbt.digid.common.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {
  private long id;
  private DeviceDTO device;
  private Long countryId;
  private Long regionId;
  private Long districtId;
  private String street;
  private String home;
  private String flat;
  private String block;
  private String additional;
  private String macAddress;
  private String ipAddress;
  private String os;
  private String appUrl;
  private String appId;
  private String clientPubKey;

}
