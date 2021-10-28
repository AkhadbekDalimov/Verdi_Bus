package uz.asbt.digid.common.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestMonitorDTO {

  private long id;
  private String guid;
  private String serialNumber;
  private LocalDateTime requestDate;
  private String documentNumber;
  private String documentType;

}
