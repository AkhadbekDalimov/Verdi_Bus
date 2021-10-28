package uz.asbt.digid.crmservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.exception.GettingListException;
import uz.asbt.digid.common.models.dto.OrganizationDTO;
import uz.asbt.digid.common.models.rest.GridRequest;
import uz.asbt.digid.common.models.rest.GridResponse;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.crmservice.exception.OrganizationFindByCrmIdException;
import uz.asbt.digid.crmservice.exception.OrganizationFindException;
import uz.asbt.digid.crmservice.exception.OrganizationSaveException;
import uz.asbt.digid.crmservice.service.OrganizationService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/organization/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrganizationController {

  OrganizationService service;
  MessageSource messageSource;

  private static final String SUCCESS = "message_0";

  @PostMapping(value = "{lang}/save")
  public ResponseEntity<Response<OrganizationDTO>> save(@PathVariable("lang") final Locale locale,
                                                        @RequestBody final OrganizationDTO organization) {
    return Optional.ofNullable(organization)
      .map(service::save)
      .map(org -> Response.<OrganizationDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(org)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(OrganizationSaveException::new);
  }

  @GetMapping(value = "{lang}/find/{id}")
  public ResponseEntity<Response<OrganizationDTO>> find(@PathVariable("lang") final Locale locale,
                                                        @PathVariable("id") final Long id) {
    return Optional.ofNullable(id)
      .map(service::findById)
      .map(org -> Response.<OrganizationDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(org)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(() -> new OrganizationFindException(id));
  }

  @GetMapping(value = "{lang}/findByOrgCrmId/{orgCrmId}")
  public ResponseEntity<Response<OrganizationDTO>> findByOrgCrmId(@PathVariable("lang") final Locale locale,
                                                                  @PathVariable("orgCrmId") final String orgCrmId) {
    return Optional.ofNullable(orgCrmId)
      .map(service::findByOrgCrmId)
      .map(org -> Response.<OrganizationDTO>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(org)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(OrganizationFindByCrmIdException::new);
  }

  @PostMapping(value = "{lang}/findAll")
  public ResponseEntity<Response<GridResponse<List<OrganizationDTO>>>> findAll(@PathVariable("lang") final Locale locale,
                                                                               @RequestBody final GridRequest gridRequest) {
    return Optional.ofNullable(gridRequest)
      .map(grid -> PageRequest.of(grid.getPage(), grid.getSize()))
      .map(service::findAll)
      .map(list -> Response.<GridResponse<List<OrganizationDTO>>>builder()
        .code(0)
        .message(messageSource.getMessage(SUCCESS, null, locale))
        .response(list)
        .build())
      .map(ResponseEntity::ok)
      .orElseThrow(GettingListException::new);
  }
}
