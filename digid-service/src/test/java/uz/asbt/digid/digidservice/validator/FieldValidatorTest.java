package uz.asbt.digid.digidservice.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;

import java.time.LocalDate;

import static uz.asbt.digid.common.constants.Const.LOCAL_DATE_JSON_FORMATTER;

@SpringBootTest
@RunWith(SpringRunner.class)
@NoArgsConstructor
public class FieldValidatorTest {


    private FieldValidator validator;

    @Autowired
    public void setValidator(FieldValidator validator) {
        this.validator = validator;
    }

    @Test
    public void testIsLiveness() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final String json = PersonAnswereJson;
        final ModelPersonAnswere answere = mapper.readValue(json, ModelPersonAnswere.class);
        final boolean result = validator.isLiveness(answere);
        Assert.assertTrue(result);
    }

    private static final String PersonAnswereJson = "{\n" +
            "  \"answere\": {\n" +
            "    \"AnswereId\": 1,\n" +
            "    \"AnswereComment\": \"OK\"\n" +
            "  },\n" +
            "  \"DocumentReadingTime\": 2.0400017999999998,\n" +
            "  \"ServiceAnswereTime\": 14.5363761,\n" +
            "  \"RequestGuid\": \"28446C74EEF54266AAA5789D81A3B95D\",\n" +
            "  \"ModelServiceInfo\": {\n" +
            "    \"Answere\": {\n" +
            "      \"AnswereId\": 1,\n" +
            "      \"AnswereComment\": \"OK\"\n" +
            "    },\n" +
            "    \"ServiceInfo\": {\n" +
            "      \"ScannerSerial\": \"32238149\",\n" +
            "      \"ProductNumber\": \"28770038\",\n" +
            "      \"Version\": \"FW NNA 21\",\n" +
            "      \"FWVersion\": \"FW CTL 2.02\",\n" +
            "      \"OCRVersion\": \"FW OCR 2.74.09\",\n" +
            "      \"ClientIP\": \"192.168.0.104\",\n" +
            "      \"ClientMAC\": \"207918502B57\",\n" +
            "      \"ClientOS\": \"Microsoft Windows 10 Pro\",\n" +
            "      \"ApplicationVersion\": \"2.0.0.102\",\n" +
            "      \"Additional\": \"\"\n" +
            "    }\n" +
            "  }\n" +
            "}";
    @Test
    public void checkExpiryDate() {
        String expiredDate = "01.01.2020";
        String nextDateOfCurrentDate = LocalDate.now().plusDays(1).format(LOCAL_DATE_JSON_FORMATTER);
        Assert.assertFalse(validator.checkExpiryDate(expiredDate));
        Assert.assertTrue(validator.checkExpiryDate(nextDateOfCurrentDate));
    }
}