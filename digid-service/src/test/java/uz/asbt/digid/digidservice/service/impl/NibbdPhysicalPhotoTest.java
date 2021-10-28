package uz.asbt.digid.digidservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.PhysicalPhotoResponse;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
@NoArgsConstructor
public class NibbdPhysicalPhotoTest {


    private NibbdPhysicalPhoto nibbdPhysicalPhoto;

    @Autowired
    public void setPassportService( @Qualifier("nibbdPhysicalPhoto") final NibbdPhysicalPhoto nibbdPhysicalPhoto) {
        this.nibbdPhysicalPhoto = nibbdPhysicalPhoto;
    }

    @Test
    public void get() throws Exception{
        final ObjectMapper mapper = new ObjectMapper();
        final String json = pasportInfoJson;
        final ModelPersonAnswere answere = mapper.readValue(json, ModelPersonAnswere.class);
        PhysicalPhotoResponse photoResponse = nibbdPhysicalPhoto.get(answere);
        assertNotNull(photoResponse.getResponse().getPhoto());
    }

    private static final String pasportInfoJson = "{\n" +
            "\t\"header\": {\n" +
            "\t\t\"bank\": \"014\",\n" +
            "\t\t\"branch\": \"01101\"\n" +
            "\t},\n" +
            "\tbody\": {\n" +
            "\t\t\"passport_seria\": \"AC\",\n" +
            "\t\t\"passport_number\": \"0547972\",\n" +
            "\t\t\"date_birth\": \"01.01.1958\",\n" +
            "\t\t\"agreement\": \"1\"\n" +
            "\t}\n" +
            "}\n";
}