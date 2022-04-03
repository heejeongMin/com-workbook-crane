package com.workbook.crane.partner.application.model.info;

import com.workbook.crane.partner.domain.model.Partner;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PartnerSearchInfo {

  private Long id;
  private String partnerNumber;
  private String companyName;
  private String ceoName;
  private String phoneNumber;
  private Long createdBy;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private LocalDateTime deletedAt;

  public static PartnerSearchInfo from(Partner partner) {
    PartnerSearchInfo info = new PartnerSearchInfo();
    info.id = partner.getId();
    info.partnerNumber = partner.getPartnerNumber();
    info.companyName = partner.getCompanyName();
    info.ceoName = partner.getCeoName();
    info.phoneNumber = partner.getPhoneNumber();
    info.createdBy = partner.getCreatedBy();
    info.createdAt = partner.getCreatedAt();
    info.modifiedAt = partner.getModifiedAt();
    info.deletedAt = partner.getDeletedAt();
    return info;
  }
}
