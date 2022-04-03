package com.workbook.crane.partner.presentation.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.workbook.crane.partner.application.model.info.PartnerCreateInfo;
import com.workbook.crane.partner.presentation.controller.PartnerController;
import com.workbook.crane.partner.presentation.request.PartnerCreateRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class PartnerCreateResponse extends RepresentationModel<PartnerCreateResponse> {

  private Long id;
  private String partnerNumber;
  private String companyName;
  private String ceoName;
  private String phoneNumber;
  private Long createdBy;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private LocalDateTime deletedAt;

  public static PartnerCreateResponse from(PartnerCreateInfo info) {
    PartnerCreateResponse response = new PartnerCreateResponse();
    response.id = info.getId();
    response.partnerNumber = info.getPartnerNumber();
    response.companyName = info.getCompanyName();
    response.ceoName = info.getCeoName();
    response.phoneNumber = info.getPhoneNumber();
    response.createdBy = info.getCreatedBy();
    response.createdAt = info.getCreatedAt();
    response.modifiedAt = info.getModifiedAt();
    response.deletedAt = info.getDeletedAt();
    return response;
  }

  public void createLink(PartnerCreateRequest request, Principal principal) throws Exception {
    this.add(linkTo(methodOn(PartnerController.class)
        .createPartner(request, principal)).withSelfRel());
    this.add(linkTo(methodOn(PartnerController.class)
        .searchPartnerByPartnerNumber(partnerNumber, principal)).withRel("href"));
    this.add(linkTo(methodOn(PartnerController.class)
        .searchAllPartner(1, 10, principal)).withRel("href"));
  }
}
