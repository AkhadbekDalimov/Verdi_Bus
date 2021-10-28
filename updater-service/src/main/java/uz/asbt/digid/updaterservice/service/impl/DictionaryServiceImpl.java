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
import uz.asbt.digid.updaterservice.dto.DictionaryListResponseDTO;
import uz.asbt.digid.updaterservice.dto.DownloadFileRequestDTO;
import uz.asbt.digid.updaterservice.service.DictionaryService;

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
 * Created by User on 29.04.2021.
 */
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

  @Value("${files.directory-dictionary}")
  String filesDirectory;

  public DictionaryListResponseDTO getDictionaryList(final CheckVersionRequestDTO dto) {
    return Optional.ofNullable(dto)
      .map(v -> DictionaryListResponseDTO.builder().files(getFiles()).build())
      .orElseThrow(EntityFindException::new);
  }

  public Resource downloadDictionary(final DownloadFileRequestDTO dto) {
    return Optional.ofNullable(dto)
      .map(v -> getFile(dto.getFileName()))
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
  private File getFile(final String fileName) {
    try (Stream<Path> paths = Files.walk(Paths.get(filesDirectory))) {
      return paths
        .filter(Files::isRegularFile)
        .filter(i -> i.getFileName().toString().equals(fileName))
        .findFirst()
        .map(Path::toFile)
        .orElseThrow(FileNotFoundException::new);
    }
  }

  @SneakyThrows
  private Collection<String> getFiles() {
    try (Stream<Path> paths = Files.walk(Paths.get(filesDirectory))) {
      return paths
        .filter(Files::isRegularFile)
        .map(path -> path.getFileName().toString())
        .collect(Collectors.toList());
    }
  }

}
