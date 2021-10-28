package uz.asbt.digid.common.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationDTO {
  private long id;
  private String orgCrmId;
  private String name;
  private String pinpp;
  private String phoneNumber;
}
