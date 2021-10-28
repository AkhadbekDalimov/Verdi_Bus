package uz.asbt.digid.updaterservice.service;

import org.springframework.core.io.Resource;
import uz.asbt.digid.updaterservice.dto.CheckVersionRequestDTO;
import uz.asbt.digid.updaterservice.dto.DictionaryListResponseDTO;
import uz.asbt.digid.updaterservice.dto.DownloadFileRequestDTO;

/**
 * Created by User on 29.04.2021.
 */

public interface DictionaryService {

    DictionaryListResponseDTO getDictionaryList(CheckVersionRequestDTO dto);
    Resource downloadDictionary(final DownloadFileRequestDTO dto);
}
