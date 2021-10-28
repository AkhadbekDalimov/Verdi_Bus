package uz.asbt.digid.digidservice.service.impl;

import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uz.asbt.digid.digidservice.model.dto.MobileDataDTO;
import uz.asbt.digid.digidservice.model.entity.MobileData;
import uz.asbt.digid.digidservice.repository.MobileDataRepository;
import uz.asbt.digid.digidservice.service.MobileDataService;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@NoArgsConstructor
@Transactional
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class MobileDataServiceImplTest {

  @Autowired
  private MobileDataRepository mobileDataRepository;

  @Autowired
  private MobileDataService mobileDataService;

  @Autowired
  private ModelMapper modelMapper;

  private static final MobileDataDTO mobileDataDTO = MobileDataDTO
    .builder()
    .mobileDeviceId("mobile device id")
    .mobileType("Mi 8")
    .mobileNumber("+988888888").build();

  private static final Optional<MobileData> mobileData = Optional.of(MobileData
    .builder()
    .id(1L)
    .mobileDeviceId("mobile device id")
    .mobileType("Mi 8")
    .mobileNumber("+988888888").build());


  @Test
  public void saveMobileDataDTO() {
    MobileDataDTO dto = mobileDataService.saveMobileDataDTO(mobileDataDTO);

    Assert.assertNotNull(dto);
    Assert.assertEquals(dto.getMobileDeviceId(), mobileDataDTO.getMobileDeviceId());
    Assert.assertEquals(dto.getMobileNumber(), mobileDataDTO.getMobileNumber());
    Assert.assertEquals(dto.getMobileType(), mobileDataDTO.getMobileType());
  }

  @Test
  public void findMobileDataByMobileInfo() {
    MobileDataDTO dto = mobileDataService.saveMobileDataDTO(mobileDataDTO);
    MobileDataDTO dataDTO = mobileDataService.findMobileDataByMobileInfo(mobileDataDTO);
    Assert.assertNotNull(dataDTO);
    Assert.assertEquals(dto.getMobileDeviceId(), dataDTO.getMobileDeviceId());
    Assert.assertEquals(dto.getMobileNumber(), dataDTO.getMobileNumber());
    Assert.assertEquals(dto.getMobileType(), dataDTO.getMobileType());
  }

}