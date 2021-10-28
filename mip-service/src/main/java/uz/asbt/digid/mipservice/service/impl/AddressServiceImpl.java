package uz.asbt.digid.mipservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import uz.asbt.digid.common.models.rest.mip.GetAddressRequest;
import uz.asbt.digid.mipservice.models.*;
import uz.asbt.digid.mipservice.service.AddressService;
import uz.asbt.digid.mipservice.types.moi.getAddress.Request;
import uz.asbt.digid.mipservice.types.moi.getAddress.ResponseModel;
import uz.asbt.digid.mipservice.types.moi.getDistrictByDistrictId.GetDistrictByDistrictId;
import uz.asbt.digid.mipservice.types.moi.getDistrictByDistrictId.GetDistrictByDistrictIdResponse;
import uz.asbt.digid.mipservice.types.moi.getDistrictsByRegionId.GetDistrictsByRegionId;
import uz.asbt.digid.mipservice.types.moi.getDistrictsByRegionId.GetDistrictsByRegionIdResponse;
import uz.asbt.digid.mipservice.types.moi.getPlaceByPlaceId.GetPlaceByPlaceId;
import uz.asbt.digid.mipservice.types.moi.getPlaceByPlaceId.GetPlaceByPlaceIdResponse;
import uz.asbt.digid.mipservice.types.moi.getPlacesByDistrictId.GetPlacesByDistrictId;
import uz.asbt.digid.mipservice.types.moi.getPlacesByDistrictId.GetPlacesByDistrictIdResponse;
import uz.asbt.digid.mipservice.types.moi.getRegionByRegionId.GetRegionByRegionId;
import uz.asbt.digid.mipservice.types.moi.getRegionByRegionId.GetRegionByRegionIdResponse;
import uz.asbt.digid.mipservice.types.moi.getRegions.GetRegions;
import uz.asbt.digid.mipservice.types.moi.getRegions.GetRegionsResponse;
import uz.asbt.digid.mipservice.types.moi.getStreetByStreetId.GetStreetByStreetId;
import uz.asbt.digid.mipservice.types.moi.getStreetByStreetId.GetStreetByStreetIdResponse;
import uz.asbt.digid.mipservice.types.moi.getStreetsByDistrictId.GetStreetsByDistrictId;
import uz.asbt.digid.mipservice.types.moi.getStreetsByDistrictId.GetStreetsByDistrictIdResponse;
import uz.asbt.digid.mipservice.types.moi.getStreetsByPlaceId.GetStreetsByPlaceId;
import uz.asbt.digid.mipservice.types.moi.getStreetsByPlaceId.GetStreetsByPlaceIdResponse;

@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    @Qualifier("webServiceAddressTemplate")
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    @Qualifier("getAddress")
    private Jaxb2Marshaller marshallerGetAddress;

    @Autowired
    @Qualifier("getDistrictByDistrictId")
    private Jaxb2Marshaller marshallerGetDistrictByDistrictId;

    @Autowired
    @Qualifier("getDistrictByRegionId")
    private Jaxb2Marshaller marshallerGetDistrictByRegionId;

    @Autowired
    @Qualifier("getPlaceByPlaceId")
    private Jaxb2Marshaller marshallerGetPlaceByPlaceId;

    @Autowired
    @Qualifier("getPlaceByDistrictId")
    private Jaxb2Marshaller marshallerGetPlaceByDistrictId;

    @Autowired
    @Qualifier("getRegionByRegionId")
    private Jaxb2Marshaller marshallerGetRegionByRegionId;

    @Autowired
    @Qualifier("getRegions")
    private Jaxb2Marshaller marshallerGetRegions;

    @Autowired
    @Qualifier("getStreetByStreetId")
    private Jaxb2Marshaller marshallerGetStreetByStreetId;

    @Autowired
    @Qualifier("getStreetsByDistrictId")
    private Jaxb2Marshaller marshallerGetStreetsByDistrictId;

    @Autowired
    @Qualifier("getStreetsByPlaceId")
    private Jaxb2Marshaller marshallerGetStreetsByPlaceId;

    @Value("${asbt.mib.server}")
    private String url;

    @Override
    public ResponseModel getAddress(GetAddressRequest request) throws Exception {
        logger.info("GetAddress: begin: {}", request.toString());
        uz.asbt.digid.mipservice.types.moi.getAddress.ObjectFactory factory = new uz.asbt.digid.mipservice.types.moi.getAddress.ObjectFactory();
        Request requestFactory = factory.createRequest();
        requestFactory.setPin(request.getPin());
        requestFactory.setGuid(request.getGuid());
        webServiceTemplate.setMarshaller(marshallerGetAddress);
        webServiceTemplate.setUnmarshaller(marshallerGetAddress);
        logger.info("Begin send request");
        return  (ResponseModel) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/MOI/GetAddress", requestFactory);
    }

    @Override
    public GetDistrictByDistrictIdResponse getDistrictByDistrictId(GetDistrictByDistrictIdRequest value) throws Exception {
        logger.info("GetDistrictByDistrictId: begin");
        uz.asbt.digid.mipservice.types.moi.getDistrictByDistrictId.ObjectFactory factory = new uz.asbt.digid.mipservice.types.moi.getDistrictByDistrictId.ObjectFactory();
        GetDistrictByDistrictId.AuthInfo authInfo = factory.createGetDistrictByDistrictIdAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetDistrictByDistrictId request = factory.createGetDistrictByDistrictId();
        request.setId(value.getId());
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetDistrictByDistrictId);
        webServiceTemplate.setUnmarshaller(marshallerGetDistrictByDistrictId);
        return  (GetDistrictByDistrictIdResponse) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/MOI/GetDistrictByDistrictId", request);
    }

    @Override
    public GetDistrictsByRegionIdResponse getDistrictsByRegionId(GetDistrictsByRegionIdRequest value) throws Exception {
        logger.info("GetDistrictsByRegionId: begin");
        uz.asbt.digid.mipservice.types.moi.getDistrictsByRegionId.ObjectFactory factory = new uz.asbt.digid.mipservice.types.moi.getDistrictsByRegionId.ObjectFactory();
        GetDistrictsByRegionId.AuthInfo authInfo = factory.createGetDistrictsByRegionIdAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetDistrictsByRegionId request = factory.createGetDistrictsByRegionId();
        request.setId(value.getId());
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetDistrictByRegionId);
        webServiceTemplate.setUnmarshaller(marshallerGetDistrictByRegionId);
        return  (GetDistrictsByRegionIdResponse) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/MOI/GetDistrictsByRegionId", request);
    }

    @Override
    public GetPlaceByPlaceIdResponse getPlaceByPlaceId(GetPlaceByPlaceIdRequest value) throws Exception {
        logger.info("GetPlaceByPlaceId: begin");
        uz.asbt.digid.mipservice.types.moi.getPlaceByPlaceId.ObjectFactory factory = new uz.asbt.digid.mipservice.types.moi.getPlaceByPlaceId.ObjectFactory();
        GetPlaceByPlaceId.AuthInfo authInfo = factory.createGetPlaceByPlaceIdAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetPlaceByPlaceId request = factory.createGetPlaceByPlaceId();
        request.setId(value.getId());
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetPlaceByPlaceId);
        webServiceTemplate.setUnmarshaller(marshallerGetPlaceByPlaceId);
        return  (GetPlaceByPlaceIdResponse) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/MOI/GetPlaceByPlaceId", request);
    }

    @Override
    public GetPlacesByDistrictIdResponse getPlacesByDistrictId(GetPlacesByDistrictIdRequest value) throws Exception {
        logger.info("GetPlacesByDistrictId: begin");
        uz.asbt.digid.mipservice.types.moi.getPlacesByDistrictId.ObjectFactory factory = new uz.asbt.digid.mipservice.types.moi.getPlacesByDistrictId.ObjectFactory();
        GetPlacesByDistrictId.AuthInfo authInfo = factory.createGetPlacesByDistrictIdAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetPlacesByDistrictId request = factory.createGetPlacesByDistrictId();
        request.setId(value.getId());
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetPlaceByDistrictId);
        webServiceTemplate.setUnmarshaller(marshallerGetPlaceByDistrictId);
        return  (GetPlacesByDistrictIdResponse) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/MOI/GetPlacesByDistrictId", request);
    }

    @Override
    public GetRegionByRegionIdResponse getRegionByRegionId(GetRegionByRegionIdRequest value) throws Exception {
        logger.info("GetRegionByRegionId: begin");
        uz.asbt.digid.mipservice.types.moi.getRegionByRegionId.ObjectFactory factory = new uz.asbt.digid.mipservice.types.moi.getRegionByRegionId.ObjectFactory();
        GetRegionByRegionId.AuthInfo authInfo = factory.createGetRegionByRegionIdAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetRegionByRegionId request = factory.createGetRegionByRegionId();
        request.setId(value.getId());
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetRegionByRegionId);
        webServiceTemplate.setUnmarshaller(marshallerGetRegionByRegionId);
        return  (GetRegionByRegionIdResponse) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/MOI/GetRegionByRegionId", request);
    }

    @Override
    public GetRegionsResponse getRegions(GetRegionsRequest value) throws Exception {
        logger.info("GetRegionse: begin");
        uz.asbt.digid.mipservice.types.moi.getRegions.ObjectFactory factory = new uz.asbt.digid.mipservice.types.moi.getRegions.ObjectFactory();
        GetRegions.AuthInfo authInfo = factory.createGetRegionsAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetRegions request = factory.createGetRegions();
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetRegions);
        webServiceTemplate.setUnmarshaller(marshallerGetRegions);
        return (GetRegionsResponse) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/MOI/GetRegions", request);
    }

    @Override
    public GetStreetByStreetIdResponse getStreetByStreetId(GetStreetByStreetIdRequest value) throws Exception {
        logger.info("GetStreetByStreetId: begin");
        uz.asbt.digid.mipservice.types.moi.getStreetByStreetId.ObjectFactory factory = new uz.asbt.digid.mipservice.types.moi.getStreetByStreetId.ObjectFactory();
        GetStreetByStreetId.AuthInfo authInfo = factory.createGetStreetByStreetIdAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetStreetByStreetId request = factory.createGetStreetByStreetId();
        request.setId(value.getId());
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetStreetByStreetId);
        webServiceTemplate.setUnmarshaller(marshallerGetStreetByStreetId);
        return (GetStreetByStreetIdResponse) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/MOI/GetStreetByStreetId", request);
    }

    @Override
    public GetStreetsByDistrictIdResponse getStreetsByDistrictId(GetStreetsByDistrictIdRequest value) throws Exception {
        logger.info("GetStreetsByDistrictId: begin");
        uz.asbt.digid.mipservice.types.moi.getStreetsByDistrictId.ObjectFactory factory = new uz.asbt.digid.mipservice.types.moi.getStreetsByDistrictId.ObjectFactory();
        GetStreetsByDistrictId.AuthInfo authInfo = factory.createGetStreetsByDistrictIdAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetStreetsByDistrictId request = factory.createGetStreetsByDistrictId();
        request.setId(value.getId());
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetStreetsByDistrictId);
        webServiceTemplate.setUnmarshaller(marshallerGetStreetsByDistrictId);
        return (GetStreetsByDistrictIdResponse) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/MOI/GetStreetsByDistrictId", request);
    }

    @Override
    public GetStreetsByPlaceIdResponse getStreetsByPlaceId(GetStreetsByPlaceIdRequest value) throws Exception {
        logger.info("GetStreetsByPlaceId: begin");
        uz.asbt.digid.mipservice.types.moi.getStreetsByPlaceId.ObjectFactory factory = new uz.asbt.digid.mipservice.types.moi.getStreetsByPlaceId.ObjectFactory();
        GetStreetsByPlaceId.AuthInfo authInfo = factory.createGetStreetsByPlaceIdAuthInfo();
        authInfo.setUserSessionId(value.getAuthInfo().getUserSessionId());
        authInfo.setWSID(value.getAuthInfo().getWsid());
        authInfo.setLEID(value.getAuthInfo().getLeid());
        GetStreetsByPlaceId request = factory.createGetStreetsByPlaceId();
        request.setId(value.getId());
        request.setAuthInfo(authInfo);
        webServiceTemplate.setMarshaller(marshallerGetStreetsByPlaceId);
        webServiceTemplate.setUnmarshaller(marshallerGetStreetsByPlaceId);
        return (GetStreetsByPlaceIdResponse) webServiceTemplate.marshalSendAndReceive(url + "/mediate/ips/MOI/GetStreetsByPlaceId", request);
    }
}
