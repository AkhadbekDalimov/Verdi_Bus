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
import uz.asbt.digid.updaterservice.dto.CheckVersionResponseDTO;
import uz.asbt.digid.updaterservice.dto.DownloadFileRequestDTO;
import uz.asbt.digid.updaterservice.service.UpdateService;

import java.util.Optional;

/**
 * Created by User on 15.01.2020.
 */
@RestController
@RequestMapping("/api/update")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UpdateController {

  UpdateService updateService;

  @PostMapping("/check-version")
  public ResponseEntity<Response<CheckVersionResponseDTO>> checkVersion(@RequestBody final CheckVersionRequestDTO dto) {
    return Optional.of(updateService.checkVersion(dto))
      .map(v -> Response.<CheckVersionResponseDTO>builder().code(0).message("OK").response(v).build())
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.ok(Response.<CheckVersionResponseDTO>builder()
        .code(0)
        .message("OK")
        .response(CheckVersionResponseDTO.builder().newVersion(dto.getCurrentClientVersion()).build()).build()));
  }

  @PostMapping("/download")
  public ResponseEntity<Resource> download(@RequestBody final DownloadFileRequestDTO dto) {
    return Optional.of(updateService.downloadFile(dto))
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
