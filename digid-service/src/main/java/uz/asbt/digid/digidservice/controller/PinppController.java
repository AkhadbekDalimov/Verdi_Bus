package uz.asbt.digid.digidservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import uz.asbt.digid.common.enums.LoginType;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.digidservice.service.PassportService;

import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pinpp")
public class PinppController {

  private final PassportService passportService;

  @PostMapping(value = "/{lang}/registration")
  public ResponseEntity<Response<ModelPersonAnswere>> registration(@RequestHeader final HttpHeaders headers,
                                                          @RequestBody final ModelPersonAnswere person,
                                                          @PathVariable("lang") final Locale locale,
                                                          final WebRequest webRequest) {
    webRequest.setAttribute("person", person, RequestAttributes.SCOPE_REQUEST);
    webRequest.setAttribute("locale", locale, RequestAttributes.SCOPE_REQUEST);
    log.info("begin registration");

    return passportService.getPassportInfoForMobile(person, locale, LoginType.REGISTRATION.getType());
  }

  @PostMapping(value = "/{lang}/verification")
  public ResponseEntity<Response<ModelPersonAnswere>> verification(@RequestHeader final HttpHeaders headers,
                                                                   @RequestBody final ModelPersonAnswere person,
                                                                   @PathVariable("lang") final  Locale locale,
                                                                   final WebRequest webRequest) {
    return passportService.getPassportInfoForMobile(person, locale, LoginType.VERIFICATION.getType());
  }

}
