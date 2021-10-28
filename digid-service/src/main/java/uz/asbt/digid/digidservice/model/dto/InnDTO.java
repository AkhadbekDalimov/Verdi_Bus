package uz.asbt.digid.digidservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InnDTO {

  private Long id;

  private String inn;

  private String innDate;

  private String taxCode;

  private PinppDTO pinpp;
}
