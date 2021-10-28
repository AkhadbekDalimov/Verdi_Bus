package uz.asbt.digid.mipservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import uz.asbt.digid.mipservice.models.DataCEPRequest;
import uz.asbt.digid.mipservice.models.DataCEPResponse;
import uz.asbt.digid.mipservice.models.PersonDocInfoResponse;
import uz.asbt.digid.mipservice.models.PersonDocInfoServiceRequest;
import uz.asbt.digid.mipservice.service.PersonService;
import uz.asbt.digid.mipservice.types.personDocInfoService.CEPRequest;
import uz.asbt.digid.mipservice.types.personDocInfoService.CEPResponse;
import uz.asbt.digid.mipservice.types.personDocInfoService.ObjectFactory;
import uz.asbt.digid.mipservice.utils.XMLUtil;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    private WebServiceTemplate webServiceTemplate;
    private Jaxb2Marshaller marshaller;

    @Value("${asbt.mib.server}")
    private String url;

    @Autowired
    public PersonServiceImpl(
            @Qualifier("webServicePersonTemplate") WebServiceTemplate webServiceTemplate,
            @Qualifier("personDocInfoService") Jaxb2Marshaller marshaller) {
        this.webServiceTemplate = webServiceTemplate;
        this.marshaller = marshaller;
    }

    @Override
    public PersonDocInfoResponse personDocInfoService(PersonDocInfoServiceRequest value) throws Exception {
        logger.info("PersonDocInfoService: begin: {}", value.toString());
        ObjectFactory factory = new ObjectFactory();

        CEPRequest.AuthInfo authInfo = factory.createCEPRequestAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());

        DataCEPRequest dataRequest = new DataCEPRequest();
        dataRequest.setPinpp(value.getData().getPinpp());
        dataRequest.setDocument(value.getData().getDocument());
        dataRequest.setLangId(value.getData().getLangId());

        String data = XMLUtil.jaxbObjectToXML(dataRequest);

        CEPRequest request = factory.createCEPRequest();
        request.setAuthInfo(authInfo);
        request.setData(data);
        request.setSignature(value.getSignature());
        request.setPublicCert(value.getPublicCert());
        request.setSignDate(value.getSingDate());

        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        logger.info("Begin send request");
        CEPResponse response = (CEPResponse) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/PC/PersonDocInfoService", request);
        logger.info("End send request");
        response.setData(response.getData().replaceAll("&lt;", "<"));
        response.setData(response.getData().replaceAll("&gt;", ">"));
        DataCEPResponse dataCEPResponse = XMLUtil.jaxbXMLToObject(response.getData());
        PersonDocInfoResponse response1 = new PersonDocInfoResponse();
        response1.setResult(response.getResult());
        response1.setData(dataCEPResponse);
        response1.setComments(response.getComments());
        logger.info("Response is: {}", response1.toString());
        return response1;
    }
}
