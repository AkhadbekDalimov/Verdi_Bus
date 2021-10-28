package uz.asbt.digid.digidservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.digidservice.service.PassportService;

import java.util.Locale;

/**
 * Controller to getting passport info
 */
@RefreshScope
@RestController
@RequestMapping("/passport")
@RequiredArgsConstructor
@Slf4j
public class PassportController {


    private final PassportService passportService;

    @SneakyThrows
    @PostMapping(value = "/{lang}/passportInfo")
    public ResponseEntity<Response<ModelPersonAnswere>> info(@RequestHeader final HttpHeaders headers,
                                                   @RequestBody final ModelPersonAnswere person,
                                                   @PathVariable("lang") final Locale locale,
                                                   final WebRequest webRequest) {
        webRequest.setAttribute("person", person, RequestAttributes.SCOPE_REQUEST);
        webRequest.setAttribute("locale", locale, RequestAttributes.SCOPE_REQUEST);
        return passportService.getPassportInfo(person, locale);
    }

}
