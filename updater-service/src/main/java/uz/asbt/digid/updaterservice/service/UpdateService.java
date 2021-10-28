package uz.asbt.digid.updaterservice.service;

import org.springframework.core.io.Resource;
import uz.asbt.digid.updaterservice.dto.CheckVersionRequestDTO;
import uz.asbt.digid.updaterservice.dto.CheckVersionResponseDTO;
import uz.asbt.digid.updaterservice.dto.DownloadFileRequestDTO;

/**
 * Created by User on 15.01.2020.
 */

public interface UpdateService {

    CheckVersionResponseDTO checkVersion(CheckVersionRequestDTO dto);
    Resource downloadFile(final DownloadFileRequestDTO dto);
}
