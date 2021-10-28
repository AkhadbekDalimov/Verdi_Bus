package uz.asbt.digid.digidservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.digidservice.model.dto.WebClientRequestDTO;
import uz.asbt.digid.digidservice.service.IWebClientService;

import java.util.Locale;

@RestController
@RequestMapping("/web")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebClientController {

  IWebClientService webClientService;

  @PostMapping("/{lang}/registration/")
  public ResponseEntity<Response<ModelPersonAnswere>> registration(@RequestBody WebClientRequestDTO request,
                                                                   @PathVariable("lang") Locale locale) {
    webClientService.checkClientBlockStatus(request);

    return null;
  }
}
