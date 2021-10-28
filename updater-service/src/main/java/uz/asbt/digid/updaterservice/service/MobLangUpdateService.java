package uz.asbt.digid.updaterservice.service;


import org.springframework.core.io.Resource;
import uz.asbt.digid.updaterservice.dto.CheckVersionRequestDTO;
import uz.asbt.digid.updaterservice.dto.CheckVersionResponseDTO;
import uz.asbt.digid.updaterservice.dto.DownloadFileRequestDTO;

public interface MobLangUpdateService {

  CheckVersionResponseDTO checkVersion(CheckVersionRequestDTO dto);
  Resource downloadFile(final DownloadFileRequestDTO dto);
}
