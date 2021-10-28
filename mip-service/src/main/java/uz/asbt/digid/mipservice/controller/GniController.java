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
import uz.asbt.digid.common.models.rest.mip.TaxInfoRequest;
import uz.asbt.digid.common.models.rest.mip.TaxInfoResponse;
import uz.asbt.digid.mipservice.exceptions.TaxNotFoundException;
import uz.asbt.digid.mipservice.models.AuthInfo;
import uz.asbt.digid.mipservice.models.GetTaxInfoByIdRequest;
import uz.asbt.digid.mipservice.models.GetTaxInfoByPinRequest;
import uz.asbt.digid.mipservice.models.GetTaxInfoByTinRequest;
import uz.asbt.digid.mipservice.service.GniService;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyId.GetTaxInfobyIdRes;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyPin.GetTaxInfobyPinRes;
import uz.asbt.digid.mipservice.types.stc.getTaxInfobyTin.GetTaxInfobyTinRes;

import java.util.*;

@RefreshScope
@RestController
@RequestMapping("/mip")
public class GniController {

    private static final Logger logger = LoggerFactory.getLogger(GniController.class);

    private Environment env;
    private MessageSource source;
    private GniService service;

    @Autowired
    public GniController(Environment env, MessageSource source, GniService service) {
        this.env = env;
        this.source = source;
        this.service = service;
    }

    @RequestMapping(value = "/gni/{lang}/infoById", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetTaxInfobyIdRes>> getById(
            @RequestBody GetTaxInfoByIdRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetTaxInfobyIdRes> response = new Response<>();
        try {
            GetTaxInfobyIdRes result = service.getTaxInfoById(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(result);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/gni/{lang}/infoByPin", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<TaxInfoResponse>> getByPin(
            @RequestBody TaxInfoRequest request,
            @PathVariable("lang") Locale locale) {
        Response<TaxInfoResponse> response = new Response<>();
        try {
            GetTaxInfobyPinRes result = service.getTaxInfoByPin(parseRequest(request));
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(parseResponse(result));
        } catch (TaxNotFoundException ex) {
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

    @RequestMapping(value = "/gni/{lang}/infoByInn", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<GetTaxInfobyTinRes>> getByInn(
            @RequestBody GetTaxInfoByTinRequest request,
            @PathVariable("lang") Locale locale) {
        Response<GetTaxInfobyTinRes> response = new Response<>();
        try {
            GetTaxInfobyTinRes result = service.getTaxInfoByTin(request);
            response.setCode(env.getProperty("ok", Integer.class));
            response.setMessage(source.getMessage("message_0", null, locale));
            response.setResponse(result);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(env.getProperty("ex9999", Integer.class));
            response.setMessage(source.getMessage("message_9999", null, locale));
        }
        return ResponseEntity.ok(response);
    }

    private TaxInfoResponse parseResponse(GetTaxInfobyPinRes response) throws TaxNotFoundException {
        if (response.getErrCode().equals("1"))
            throw new TaxNotFoundException(1, "Данные не найдены");
        TaxInfoResponse infoResponse = new TaxInfoResponse();
        infoResponse.setBranchId(response.getRoot().getList().getBranchId());
        infoResponse.setDocNum(response.getRoot().getList().getDocNum());
        infoResponse.setRegDate(response.getRoot().getList().getRegDate());
        infoResponse.setStatus(response.getRoot().getList().getStatus());
        infoResponse.setTin(response.getRoot().getList().getTin());
        return infoResponse;
    }

    private GetTaxInfoByPinRequest parseRequest(TaxInfoRequest request) {
        GetTaxInfoByPinRequest pinRequest = new GetTaxInfoByPinRequest();
        pinRequest.setPin(request.getPin());
        pinRequest.setAction("get_by_pin");
        AuthInfo authInfo = new AuthInfo();
        authInfo.setUserSessionId(String.valueOf(new Random(1000).nextInt()));
        authInfo.setLeid("");
        authInfo.setWsid("");
        pinRequest.setAuthInfo(authInfo);
        pinRequest.setLang(request.getLang());
        return pinRequest;
    }

}
