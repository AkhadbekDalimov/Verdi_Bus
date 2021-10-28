package uz.asbt.digid.mipservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.common.models.rest.mip.PersonInfoRequest;
import uz.asbt.digid.common.models.rest.mip.PersonInfoResponse;
import uz.asbt.digid.mipservice.exceptions.PersonInfoException;
import uz.asbt.digid.mipservice.models.*;
import uz.asbt.digid.mipservice.service.PersonService;

import java.util.Locale;
import java.util.Random;

@RefreshScope
@RestController
@RequestMapping("/mip")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private Environment env;
    private MessageSource source;
    private PersonService service;

    @Autowired
    public PersonController(PersonService service, Environment env, MessageSource source) {
        this.service = service;
        this.env = env;
        this.source = source;
    }

    @RequestMapping(value = "/person/{lang}/info", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<PersonInfoResponse>> info(
            @RequestBody PersonInfoRequest request,
            @PathVariable("lang") Locale locale) {
        Response<PersonInfoResponse> response = new Response<>();
        try {
            PersonDocInfoResponse infoResponse = service.personDocInfoService(parseRequest(request));
            if (!infoResponse.getResult().equals("1"))
                throw new PersonInfoException(Integer.parseInt(infoResponse.getResult()), "Сервис не отвечает");
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            DataCEPResponseRow data = infoResponse.getData().row;
            response.setResponse(parseResponse(data));
        } catch (PersonInfoException ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex6000", Integer.class));
            response.setMessage(source.getMessage("message_6000", null, locale));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    private PersonDocInfoServiceRequest parseRequest(PersonInfoRequest infoRequest) {
        PersonDocInfoServiceRequest request = new PersonDocInfoServiceRequest();
        AuthInfo authInfo = new AuthInfo();
        authInfo.setUserSessionId(String.valueOf(new Random(1000).nextInt()));
        authInfo.setLeid("");
        authInfo.setWsid("");
        request.setAuthInfo(authInfo);
        DataCEPRequest cepRequest = new DataCEPRequest();
        cepRequest.setDocument(infoRequest.getDocument());
        cepRequest.setPinpp(infoRequest.getPinpp());
        cepRequest.setLangId(infoRequest.getLangId());
        request.setData(cepRequest);
        request.setPublicCert("");
        request.setSignature("");
        request.setSingDate("");
        return request;
    }

    private PersonInfoResponse parseResponse(DataCEPResponseRow data) {
        PersonInfoResponse infoResponse = new PersonInfoResponse();
        infoResponse.setBirthCountry(data.birthCountry);
        infoResponse.setBirthCountryId(data.birthCountryId);
        infoResponse.setDocument(data.document);
        infoResponse.setBirthDate(data.birthDate);
        infoResponse.setBirthPlace(data.birthPlace);
        infoResponse.setBirthPlaceId(data.birthPlaceId);
        infoResponse.setSurnameEngl(data.surnameEngl);
        infoResponse.setSurnameLatin(data.surnameLatin);
        infoResponse.setNameEngl(data.nameEngl);
        infoResponse.setNameLatin(data.nameLatin);
        infoResponse.setPatronymLatin(data.patronymLatin);
        infoResponse.setLivestatus(data.livestatus);
        infoResponse.setNationality(data.nationality);
        infoResponse.setNationalityId(data.nationalityId);
        infoResponse.setCitizenship(data.citizenship);
        infoResponse.setCitizenshipId(data.citizenshipId);
        infoResponse.setSex(data.sex);
        infoResponse.setDocGivePlace(data.docGivePlace);
        infoResponse.setDocGivePlaceId(data.docGivePlaceId);
        infoResponse.setDateBeginDocument(data.dateBeginDocument);
        infoResponse.setDateEndDocument(data.dateEndDocument);
        return infoResponse;
    }

}
