package uz.asbt.digid.digidservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.asbt.digid.common.models.dto.ClientDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocReadDataDTO {

  private Long id;

  private ClientDTO client;

  private PassportDataDTO passportData;

  private String readDate;
}
