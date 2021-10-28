package uz.asbt.digid.digidservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.models.entity.client.LivenessAnswere;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.digidservice.service.PhotoValidationService;

import java.util.Locale;

@RestController
@RequestMapping("/photo/validation")
@RequiredArgsConstructor
public class PhotoValidationController {

  private final PhotoValidationService photoValidationService;

  @PostMapping("/{lang}/liveness")
  public ResponseEntity<Response<LivenessAnswere>> checkLiveness(@RequestHeader final HttpHeaders headers,
                                                                   @RequestBody final ModelPersonAnswere person,
                                                                   @PathVariable("lang") final Locale locale) {

    return photoValidationService.checkLiveness(person);
  }

  @PostMapping("/{lang}/similarity")
  public ResponseEntity<Response<LivenessAnswere>> checkSimilarity(@RequestHeader final HttpHeaders headers,
                                                                   @RequestBody final ModelPersonAnswere person,
                                                                   @PathVariable("lang") final Locale locale) {

    return photoValidationService.checkSimilarity(person);
  }

  @PostMapping("/{lang}/validate")
  public ResponseEntity<Response<LivenessAnswere>> validate(@RequestHeader final HttpHeaders headers,
                                                                   @RequestBody final ModelPersonAnswere person,
                                                                   @PathVariable("lang") final Locale locale) {

    return photoValidationService.validate(person);
  }
}
