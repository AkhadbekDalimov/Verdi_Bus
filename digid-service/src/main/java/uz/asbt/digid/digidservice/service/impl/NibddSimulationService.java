package uz.asbt.digid.digidservice.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import uz.asbt.digid.common.exception.IntegrationException;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.PhysicalResponse;
import uz.asbt.digid.digidservice.service.IntegrationService;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service("nibddSimulation")
public class NibddSimulationService implements IntegrationService<ModelPersonAnswere, PhysicalResponse> {



  @Override
  public PhysicalResponse get(final ModelPersonAnswere request) throws IntegrationException {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      final String json = JSON;
      final PhysicalResponse responseEntity = mapper.readValue(json, PhysicalResponse.class);
      return responseEntity;
    } catch (final FileNotFoundException e) {
      e.printStackTrace();
    } catch (final JsonParseException e) {
      e.printStackTrace();
    } catch (final JsonMappingException e) {
      e.printStackTrace();
    } catch (final IOException e) {
      e.printStackTrace();
    }
    return null;
  }


  private static final String JSON = "{\"result\":{\"code\":\"02000\",\"message\":\"Успешно\"},\"header\":{\"query_id\":\"2146UUP146W7H1159JNF\",\"inquire\":\"26.05.2021 11:59:45\",\"respond\":\"26.05.2021 11:59:46\"},\"request\":{\"inn\":\"\",\"pin\":\"32201730230027\",\"passport_seria\":\"FA\",\"passport_number\":\"2482669\",\"date_birth\":\"22.01.1973\",\"agreement\":\"1\"},\"response\":{\"subject_state\":\"1\",\"inn\":\"454413317\",\"inn_registrated\":\"18.03.2003\",\"inn_registrated_gni\":\"2607\",\"pin\":\"32201730230027\",\"last_name\":\"MURSALIMOV\",\"first_name\":\"RUSLAN\",\"patronym\":\"NAILEVICH\",\"surname\":\"\",\"givenname\":\"\",\"birth_date\":\"22.01.1973\",\"sex\":\"1\",\"passport_seria\":\"AD\",\"passport_number\":\"0111701\",\"date_issue\":\"02.02.2021\",\"date_expiry\":\"01.02.2031\",\"give_place\":\"26266\",\"give_place_name\":\"ЮНУСАБАДСКИЙ РУВД ГОРОДА ТАШКЕНТА\",\"birth_country\":\"860\",\"birth_country_name\":\"O‘ZBEKISTON\",\"birth_place\":\"NAMANGAN VILOYATI\",\"nationality\":\"038\",\"nationality_desc\":\"TATAR\",\"citizenship\":\"182\",\"citizenship_name\":\"УЗБЕКИСТАН\",\"domicile_kadastr\":\"10:07:04:01:02:5916\",\"domicile_country\":\"860\",\"domicile_region\":\"26\",\"domicile_district\":\"200\",\"domicile_place_desc\":\"\",\"domicile_street_desc\":\"\",\"domicile_address\":\"БОДОМЗОР МФЙ, ФИРДАВСИЙ КЎЧАСИ,  uy:151\",\"domicile_house\":\"\",\"domicile_block\":\"\",\"domicile_flat\":\"\",\"domicile_begin\":\"\",\"temp_kadastr\":\"\",\"temp_country\":\"\",\"temp_region\":\"\",\"temp_district\":\"\",\"temp_place_desc\":\"\",\"temp_street_desc\":\"\",\"temp_address\":\"\",\"temp_house\":\"\",\"temp_block\":\"\",\"temp_flat\":\"\",\"temp_begin\":\"\",\"temp_end\":\"\"}}";
}