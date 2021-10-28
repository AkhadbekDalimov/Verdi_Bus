package uz.asbt.digid.common.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceDTO {
  private long id;
  private String orgCrmId;
  private String deviceCrmId;
  private long deviceTypeId;
  private String serialNumber;
  private long status;
  private boolean test;
  private OrganizationDTO organization;
  private int livenessCheck;
  private int similarityCheck;
  private boolean liveness;
  private ValidationScoresDTO validationScores;

}
