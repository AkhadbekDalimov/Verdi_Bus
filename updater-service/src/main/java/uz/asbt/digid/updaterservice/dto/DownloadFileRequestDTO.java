package uz.asbt.digid.updaterservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DownloadFileRequestDTO implements CurrentVersion {

  String currentClientVersion;
  String deviceSerialNumber;
  String fileName;
  String signString;

}
