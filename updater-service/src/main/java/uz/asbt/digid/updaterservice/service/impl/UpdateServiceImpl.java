package uz.asbt.digid.updaterservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.exception.EntityFindException;
import uz.asbt.digid.common.exception.FileNotFoundException;
import uz.asbt.digid.updaterservice.dto.CheckVersionRequestDTO;
import uz.asbt.digid.updaterservice.dto.CheckVersionResponseDTO;
import uz.asbt.digid.updaterservice.dto.DownloadFileRequestDTO;
import uz.asbt.digid.updaterservice.model.Version;
import uz.asbt.digid.updaterservice.repository.VersionRepository;
import uz.asbt.digid.updaterservice.service.UpdateService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by User on 15.01.2020.
 */
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UpdateServiceImpl implements UpdateService {

  final VersionRepository versionRepository;

  @Value("${files.directory}")
  String filesDirectory;

  public CheckVersionResponseDTO checkVersion(final CheckVersionRequestDTO dto) {
    return Optional.ofNullable(dto)
      .map(v -> versionRepository.findByVersion())
      .map(Version::getCurrentVersion)
      .map(v -> CheckVersionResponseDTO.builder().newVersion(v).files(getFiles(v)).build())
      .orElseThrow(EntityFindException::new);
  }

  public Resource downloadFile(final DownloadFileRequestDTO dto) {
    return Optional.ofNullable(dto)
      .map(v -> getFile(v.getCurrentClientVersion(), dto.getFileName()))
      .map(file -> {
        try {
          log.info("File: {}", file.getName());
          return new  ByteArrayResource(Base64.encodeBase64(Files.readAllBytes(file.toPath())));
        } catch (IOException e) {
          e.printStackTrace();
        }
        return null;
      })
      .orElseThrow(FileNotFoundException::new);
  }

  @SneakyThrows
  private File getFile(final String version, final String fileName) {
    try (Stream<Path> paths = Files.walk(Paths.get(filesDirectory + version))) {
      return paths
        .filter(Files::isRegularFile)
        .filter(i -> i.getFileName().toString().equals(fileName))
        .findFirst()
        .map(Path::toFile)
        .orElseThrow(FileNotFoundException::new);
    }
  }

  @SneakyThrows
  private Collection<String> getFiles(final String version) {
    try (Stream<Path> paths = Files.walk(Paths.get(filesDirectory + version))) {
      return paths
        .filter(Files::isRegularFile)
        .map(path -> path.getFileName().toString())
        .collect(Collectors.toList());
    }
  }

}
