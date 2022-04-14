package com.workbook.crane.partner.presentation.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.workbook.crane.partner.application.model.command.PartnerCreateCommand;
import com.workbook.crane.partner.presentation.response.PartnerCreateResponse;
import com.workbook.crane.partner.presentation.response.PartnerSearchByCriteriaRes;
import com.workbook.crane.partner.presentation.response.PartnerSearchByPartnerNumberResponse;
import com.workbook.crane.user.application.service.AuthService;
import com.workbook.crane.user.domain.model.User;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workbook.crane.partner.application.service.PartnerService;
import com.workbook.crane.partner.presentation.request.PartnerCreateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PartnerController {

  private final PartnerService partnerService;
  private final AuthService authService;

  @GetMapping(value = "/crane/v1/partner", produces = {"application/hal+json"})
  public ResponseEntity<PartnerSearchByCriteriaRes> searchAllPartner(
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      Principal principal) throws Exception {

    log.info("page " + page);
    log.info("size " + size);
    log.info("name " + principal.getName());

    User user = authService.getUserOrElseThrow(principal.getName());
    PartnerSearchByCriteriaRes response =
        PartnerSearchByCriteriaRes.from(
            partnerService.searchPartnerAll(user.getId(), page, size));
    response.add(linkTo(methodOn(PartnerController.class)
        .searchAllPartner(page, size, principal)).withSelfRel());

    response.getPartners()
        .forEach(partner -> {
              try {
                response.add(linkTo(methodOn(PartnerController.class)
                    .searchPartnerByPartnerNumber(partner.getPartnerNumber(), principal))
                    .withRel("href"));
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
        );

    response.getPartners().forEach(p -> {
            log.info("partnernumber " +p.getPartnerNumber());
            log.info("companyName" + p.getCompanyName());});

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/crane/v1/partner/{partnerNumber}", produces = {"application/hal+json"})
  public ResponseEntity<PartnerSearchByPartnerNumberResponse> searchPartnerByPartnerNumber(
      @PathVariable(value = "partnerNumber") String partnerNumber, Principal principal)
      throws Exception {
    PartnerSearchByPartnerNumberResponse response =
        PartnerSearchByPartnerNumberResponse.from(
            partnerService.searchPartnerByPartnerNumber(partnerNumber, principal.getName()));
    response.add(linkTo(PartnerController.class).slash(response.getPartnerNumber()).withSelfRel());
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "/crane/v1/partner", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<PartnerCreateResponse> createPartner(
      @RequestBody PartnerCreateRequest partnerCreateRequest, Principal principal)
      throws Exception {
    PartnerCreateResponse response =
        PartnerCreateResponse.from(partnerService.createPartner(
            PartnerCreateCommand.from(partnerCreateRequest), principal.getName()));

    response.createLink(partnerCreateRequest, principal);

    return ResponseEntity.created(
            linkTo(PartnerController.class).slash(response.getPartnerNumber()).toUri())
        .body(response);
  }

//  @PutMapping(value = "/crane/v1/partner/{partnerId}")
//  public ResponseEntity<PartnerRes> updatePartner(
//      @PathVariable(value = "partnerId") Long partnerId,
//      @RequestBody PartnerCreateRequest partnerReq) throws Exception {
//    return ResponseEntity.ok(
//        PartnerRes.from(Arrays.asList(
//            partnerService.updatePartner(PartnerDto.of(partnerId, partnerReq)))));
//  }

//  @DeleteMapping(value = "/crane/v1/partner/{partnerId}")
//  public ResponseEntity<PartnerRes> deletePartner(
//      @PathVariable(value = "partnerId") Long partnerId) throws Exception {
//    return ResponseEntity.ok(
//        PartnerRes.from(Arrays.asList(partnerService.deletePartner(partnerId))));
//  }

}
