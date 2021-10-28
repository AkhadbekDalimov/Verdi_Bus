package uz.asbt.digid.crmservice.service.impl;

import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uz.asbt.digid.common.models.dto.ClientDTO;
import uz.asbt.digid.crmservice.service.ClientService;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@NoArgsConstructor
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ClientServiceImplTest {

  @Autowired
  private ClientService clientService;

  final ClientDTO clientDTO = TestDataProvider.CLIENT;


  @Test
  public void save() {
    clientService.save(clientDTO);

    Optional<ClientDTO> dto = clientService.findByMacAddressAndSerialNumber(clientDTO.getMacAddress(), clientDTO.getDevice().getSerialNumber());

    Assert.assertNotNull(dto.isPresent());
    Assert.assertEquals(dto.get().getMacAddress(), clientDTO.getMacAddress());
    Assert.assertEquals(dto.get().getDevice(), clientDTO.getDevice());
    Assert.assertEquals(dto.get().getDevice().getSerialNumber(), clientDTO.getDevice().getSerialNumber());

  }

  @Test
  public void deleteClientByDeviceSerialNumber() {
    clientService.save(clientDTO);
    Optional<ClientDTO> dto = clientService.findByMacAddressAndSerialNumber(TestDataProvider.CLIENT.getMacAddress(),
      TestDataProvider.DEVICE.getSerialNumber());

    Assert.assertNotNull(dto.isPresent());
    Assert.assertEquals(dto.get().getMacAddress(), clientDTO.getMacAddress());
    Assert.assertEquals(dto.get().getDevice(), clientDTO.getDevice());
    Assert.assertEquals(dto.get().getDevice().getSerialNumber(), clientDTO.getDevice().getSerialNumber());

  }

}