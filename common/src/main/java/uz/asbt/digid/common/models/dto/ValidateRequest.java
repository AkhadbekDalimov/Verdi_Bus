package uz.asbt.digid.common.models.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidateRequest {

  @NotEmpty
  String serialNumber;

  @NotEmpty
  String deviceSerialNumber;

  @NotEmpty
  String guid;

//  @NotEmpty
  String pinpp;

//  @NotEmpty
  String basePhoto;

  @NotEmpty
  String additionalPhoto;

}
