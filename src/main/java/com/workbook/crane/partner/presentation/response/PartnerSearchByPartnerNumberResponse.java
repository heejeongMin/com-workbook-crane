package com.workbook.crane.partner.presentation.response;

import com.workbook.crane.partner.application.model.info.PartnerCreateInfo;
import com.workbook.crane.partner.application.model.info.PartnerSearchInfo;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumSet;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class PartnerSearchByPartnerNumberResponse extends
    RepresentationModel<PartnerSearchByPartnerNumberResponse> {

  private Long id;
  private String partnerNumber;
  private String companyName;
  private String ceoName;
  private String phoneNumber;
  private Long createdBy;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private LocalDateTime deletedAt;

  public static PartnerSearchByPartnerNumberResponse from(PartnerSearchInfo info) {
    PartnerSearchByPartnerNumberResponse response = new PartnerSearchByPartnerNumberResponse();
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

  public String test(){
    return "Test";
  }
}
