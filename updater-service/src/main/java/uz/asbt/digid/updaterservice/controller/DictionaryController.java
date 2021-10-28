package uz.asbt.digid.updaterservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.exception.FileNotFoundException;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.updaterservice.dto.CheckVersionRequestDTO;
import uz.asbt.digid.updaterservice.dto.DictionaryListResponseDTO;
import uz.asbt.digid.updaterservice.dto.DownloadFileRequestDTO;
import uz.asbt.digid.updaterservice.service.DictionaryService;

import java.util.Optional;

/**
 * Created by User on 29.04.2021.
 */
@RestController
@RequestMapping("/api/dictionary")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class DictionaryController {

  DictionaryService dictionaryService;

  @PostMapping("/get-list")
  public ResponseEntity<Response<DictionaryListResponseDTO>> checkVersion(@RequestBody final CheckVersionRequestDTO dto) {
    return Optional.of(dictionaryService.getDictionaryList(dto))
      .map(v -> Response.<DictionaryListResponseDTO>builder().code(0).message("OK").response(v).build())
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.ok(Response.<DictionaryListResponseDTO>builder()
        .code(0)
        .message("OK")
        .response(DictionaryListResponseDTO.builder().build()).build()));
  }

  @PostMapping("/download")
  public ResponseEntity<Resource> download(@RequestBody final DownloadFileRequestDTO dto) {
    return Optional.of(dictionaryService.downloadDictionary(dto))
      .map(file -> {
        try {
          return ResponseEntity
            .ok()
            .contentLength(file.contentLength())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(file);
        } catch (Exception ex) {
          throw new FileNotFoundException();
        }
      })
      .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
  }

}
