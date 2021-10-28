package uz.asbt.digid.updaterservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import uz.asbt.digid.updaterservice.dto.DownloadFileRequestDTO;
import uz.asbt.digid.updaterservice.service.UpdateService;

@SpringBootTest
@RunWith(SpringRunner.class)
@NoArgsConstructor
public class UpdateServiceImplTest {

  @Autowired
  private UpdateService updateService;

  @Test
  public void downloadFile() {
    final Resource resource = updateService.downloadFile(DownloadFileRequestDTO.builder().currentClientVersion("1.0.0").build());
    Assert.assertNotNull(resource);
  }
}