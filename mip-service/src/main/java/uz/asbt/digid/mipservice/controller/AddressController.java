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
import uz.asbt.digid.common.models.rest.mip.GetAddressRequest;
import uz.asbt.digid.common.models.rest.mip.GetAddressResponse;
import uz.asbt.digid.common.models.rest.mip.PermanentAddress;
import uz.asbt.digid.common.models.rest.mip.TemproaryAddress;
import uz.asbt.digid.mipservice.models.*;
import uz.asbt.digid.mipservice.service.AddressService;
import uz.asbt.digid.mipservice.types.moi.getAddress.ResponseModel;
import uz.asbt.digid.mipservice.types.moi.getDistrictByDistrictId.GetDistrictByDistrictIdResponse;
import uz.asbt.digid.mipservice.types.moi.getDistrictsByRegionId.GetDistrictsByRegionIdResponse;
import uz.asbt.digid.mipservice.types.moi.getPlaceByPlaceId.GetPlaceByPlaceIdResponse;
import uz.asbt.digid.mipservice.types.moi.getPlacesByDistrictId.GetPlacesByDistrictIdResponse;
import uz.asbt.digid.mipservice.types.moi.getRegionByRegionId.GetRegionByRegionIdResponse;
import uz.asbt.digid.mipservice.types.moi.getRegions.GetRegionsResponse;
import uz.asbt.digid.mipservice.types.moi.getStreetByStreetId.GetStreetByStreetIdResponse;
import uz.asbt.digid.mipservice.types.moi.getStreetsByDistrictId.GetStreetsByDistrictIdResponse;
import uz.asbt.digid.mipservice.types.moi.getStreetsByPlaceId.GetStreetsByPlaceIdResponse;

import java.util.Locale;

@RefreshScope
@RestController
@RequestMapping("/mip")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    private Environment env;
    private MessageSource source;
    private AddressService service;

    @Autowired
    public AddressController(Environment env, MessageSource source, AddressService service) {
        this.env = env;
        this.source = source;
        this.service = service;
    }

    @RequestMapping(value = "/address/{lang}/info", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetAddressResponse>> getAddress(
            @RequestBody GetAddressRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetAddressResponse> response = new Response<>();
        try {
            ResponseModel responseModel = service.getAddress(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(parseResponse(responseModel));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }


    @RequestMapping(value = "/address/{lang}/districtById", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetDistrictByDistrictIdResponse>> getDistrictById(
            @RequestBody GetDistrictByDistrictIdRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetDistrictByDistrictIdResponse> response = new Response<>();
        try {
            GetDistrictByDistrictIdResponse district = service.getDistrictByDistrictId(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(district);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/address/{lang}/districtByRegionId", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetDistrictsByRegionIdResponse>> getDistrictByRegionId(
            @RequestBody GetDistrictsByRegionIdRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetDistrictsByRegionIdResponse> response = new Response<>();
        try {
            GetDistrictsByRegionIdResponse district = service.getDistrictsByRegionId(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(district);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/address/{lang}/placeById", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetPlaceByPlaceIdResponse>> placeById(
            @RequestBody GetPlaceByPlaceIdRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetPlaceByPlaceIdResponse> response = new Response<>();
        try {
            GetPlaceByPlaceIdResponse district = service.getPlaceByPlaceId(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(district);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/address/{lang}/placeByDistrictId", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetPlacesByDistrictIdResponse>> placeByDistrictId(
            @RequestBody GetPlacesByDistrictIdRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetPlacesByDistrictIdResponse> response = new Response<>();
        try {
            GetPlacesByDistrictIdResponse district = service.getPlacesByDistrictId(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(district);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/address/{lang}/regionById", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetRegionByRegionIdResponse>> regionById(
            @RequestBody GetRegionByRegionIdRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetRegionByRegionIdResponse> response = new Response<>();
        try {
            GetRegionByRegionIdResponse district = service.getRegionByRegionId(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(district);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/address/{lang}/regions", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetRegionsResponse>> regions(
            @RequestBody GetRegionsRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetRegionsResponse> response = new Response<>();
        try {
            GetRegionsResponse district = service.getRegions(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(district);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/address/{lang}/streetById", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetStreetByStreetIdResponse>> streetById(
            @RequestBody GetStreetByStreetIdRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetStreetByStreetIdResponse> response = new Response<>();
        try {
            GetStreetByStreetIdResponse district = service.getStreetByStreetId(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(district);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/address/{lang}/streetByDistrictId", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetStreetsByDistrictIdResponse>> streetByDistrictId(
            @RequestBody GetStreetsByDistrictIdRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetStreetsByDistrictIdResponse> response = new Response<>();
        try {
            GetStreetsByDistrictIdResponse district = service.getStreetsByDistrictId(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(district);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/address/{lang}/streetByPlaceId", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetStreetsByPlaceIdResponse>> streetByPlaceId(
            @RequestBody GetStreetsByPlaceIdRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetStreetsByPlaceIdResponse> response = new Response<>();
        try {
            GetStreetsByPlaceIdResponse district = service.getStreetsByPlaceId(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(district);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    private GetAddressResponse parseResponse(ResponseModel responseModel) {
        logger.info("Begin parse response");
        GetAddressResponse response = new GetAddressResponse();
        PermanentAddress permanentAddress = new PermanentAddress();
        permanentAddress.setpCountry(responseModel.getPPermanentAddress().getPCountry());
        permanentAddress.setpRegion(responseModel.getPPermanentAddress().getPRegion());
        permanentAddress.setpDistrict(responseModel.getPPermanentAddress().getPDistrict());
        permanentAddress.setpPlace(responseModel.getPPermanentAddress().getPPlace());
        permanentAddress.setpStreet(responseModel.getPPermanentAddress().getPStreet());
        permanentAddress.setpAddress(responseModel.getPPermanentAddress().getPAddress());
        permanentAddress.setpHouse(responseModel.getPPermanentAddress().getPHouse());
        permanentAddress.setpBlock(responseModel.getPPermanentAddress().getPBlock());
        permanentAddress.setpFlat(responseModel.getPPermanentAddress().getPFlat());
        permanentAddress.setpRegtill(responseModel.getPPermanentAddress().getPRegtill());
        permanentAddress.setpRegdate(responseModel.getPPermanentAddress().getPRegdate());
        response.setpPermanentAddress(permanentAddress);
        response.setpPinpp(responseModel.getPPinpp());
        response.setpRequestGuid(responseModel.getPRequestGuid());
        TemproaryAddress temproaryAddress = new TemproaryAddress();
        temproaryAddress.setpCountry(responseModel.getPTemproaryAddress().getPCountry());
        temproaryAddress.setpRegion(responseModel.getPTemproaryAddress().getPRegion());
        temproaryAddress.setpDistrict(responseModel.getPTemproaryAddress().getPDistrict());
        temproaryAddress.setpStreet(responseModel.getPTemproaryAddress().getPStreet());
        temproaryAddress.setpPlace(responseModel.getPTemproaryAddress().getPPlace());
        temproaryAddress.setpAddress(responseModel.getPTemproaryAddress().getPAddress());
        temproaryAddress.setpHouse(responseModel.getPTemproaryAddress().getPHouse());
        temproaryAddress.setpFlat(responseModel.getPTemproaryAddress().getPFlat());
        temproaryAddress.setpBlock(responseModel.getPTemproaryAddress().getPBlock());
        temproaryAddress.setpRegdate(responseModel.getPTemproaryAddress().getPRegdate());
        temproaryAddress.setpRegtill(responseModel.getPTemproaryAddress().getPRegtill());
        response.setpTemproaryAddress(temproaryAddress);
        logger.info("End parse response");
        return response;
    }

}
