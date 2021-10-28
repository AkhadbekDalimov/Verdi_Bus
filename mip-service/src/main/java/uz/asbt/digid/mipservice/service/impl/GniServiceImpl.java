package uz.asbt.digid.mipservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import uz.asbt.digid.mipservice.models.GetTaxInfoByIdRequest;
import uz.asbt.digid.mipservice.models.GetTaxInfoByPinRequest;
import uz.asbt.digid.mipservice.models.GetTaxInfoByTinRequest;
import uz.asbt.digid.mipservice.service.GniService;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyId.GetTaxInfobyIdReq;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyId.GetTaxInfobyIdRes;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyPin.GetTaxInfobyPinReq;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyPin.GetTaxInfobyPinRes;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyTin.GetTaxInfobyTinReq;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyTin.GetTaxInfobyTinRes;

@Service
public class GniServiceImpl implements GniService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    @Qualifier("getTaxInfoById")
    private Jaxb2Marshaller marshallerGetTaxInfoById;

    @Autowired
    @Qualifier("getTaxInfoByPin")
    private Jaxb2Marshaller marshallerGetTaxInfoByPin;

    @Autowired
    @Qualifier("getTaxInfoByTin")
    private Jaxb2Marshaller marshallerGetTaxInfoByTin;

    @Autowired
    @Qualifier("webServiceGniTemplate")
    private WebServiceTemplate webServiceTemplate;

    @Value("${asbt.mib.server}")
    private String url;

    @Override
    public GetTaxInfobyIdRes getTaxInfoById(GetTaxInfoByIdRequest value) throws Exception {
        logger.info("GetTaxInfobyId: begin");
        uz.asbt.digid.mipservice.types.stc.getTaxInfobyId.ObjectFactory factory = new uz.asbt.digid.mipservice.types.stc.getTaxInfobyId.ObjectFactory();
        GetTaxInfobyIdReq.AuthInfo authInfo = factory.createGetTaxInfobyIdReqAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetTaxInfobyIdReq request = factory.createGetTaxInfobyIdReq();
        request.setAction(value.getAction());
        request.setAbranchId(value.getAbranchId());
        request.setLang(value.getLang());
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetTaxInfoById);
        webServiceTemplate.setUnmarshaller(marshallerGetTaxInfoById);
        return  (GetTaxInfobyIdRes) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/STC/GetTaxInfobyId", request);
    }

    @Override
    public GetTaxInfobyPinRes getTaxInfoByPin(GetTaxInfoByPinRequest value) throws Exception {
        logger.info("GetTaxInfobyPin: begin");
        uz.asbt.digid.mipservice.types.stc.getTaxInfobyPin.ObjectFactory factory = new uz.asbt.digid.mipservice.types.stc.getTaxInfobyPin.ObjectFactory();
        GetTaxInfobyPinReq.AuthInfo authInfo = factory.createGetTaxInfobyPinReqAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetTaxInfobyPinReq request = factory.createGetTaxInfobyPinReq();
        request.setAction(value.getAction());
        request.setPin(value.getPin());
        request.setLang(value.getLang());
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetTaxInfoByPin);
        webServiceTemplate.setUnmarshaller(marshallerGetTaxInfoByPin);
        return  (GetTaxInfobyPinRes) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/STC/GetTaxInfobyPin", request);
    }

    @Override
    public GetTaxInfobyTinRes getTaxInfoByTin(GetTaxInfoByTinRequest value) throws Exception {
        logger.info("GetTaxInfobyTin: begin");
        uz.asbt.digid.mipservice.types.stc.getTaxInfobyTin.ObjectFactory factory = new uz.asbt.digid.mipservice.types.stc.getTaxInfobyTin.ObjectFactory();
        GetTaxInfobyTinReq.AuthInfo authInfo = factory.createGetTaxInfobyTinReqAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetTaxInfobyTinReq request = factory.createGetTaxInfobyTinReq();
        request.setAction(value.getAction());
        request.setTin(value.getTin());
        request.setLang(value.getLang());
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetTaxInfoByTin);
        webServiceTemplate.setUnmarshaller(marshallerGetTaxInfoByTin);
        return  (GetTaxInfobyTinRes) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/STC/GetTaxInfobyTin", request);
    }
}
