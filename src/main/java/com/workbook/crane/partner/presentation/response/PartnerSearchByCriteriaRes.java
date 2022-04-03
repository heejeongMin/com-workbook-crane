package com.workbook.crane.partner.presentation.response;

import com.workbook.crane.partner.application.model.info.PartnerSearchInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class PartnerSearchByCriteriaRes extends RepresentationModel<PartnerSearchByCriteriaRes> {

  List<Partner> partners;

  public static PartnerSearchByCriteriaRes from(List<PartnerSearchInfo> infos) {
    PartnerSearchByCriteriaRes response = new PartnerSearchByCriteriaRes();
    response.partners = infos.stream().map(Partner::from).collect(Collectors.toList());
    return response;
  }

  @Getter
  public static class Partner {

    private Long id;
    private String partnerNumber;
    private String companyName;
    private String ceoName;
    private String phoneNumber;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static Partner from(PartnerSearchInfo info) {
      Partner partner = new Partner();
      partner.id = info.getId();
      partner.partnerNumber = info.getPartnerNumber();
      partner.companyName = info.getCompanyName();
      partner.ceoName = info.getCeoName();
      partner.phoneNumber = info.getPhoneNumber();
      partner.createdBy = info.getCreatedBy();
      partner.createdAt = info.getCreatedAt();
      partner.modifiedAt = info.getModifiedAt();
      partner.deletedAt = info.getDeletedAt();
      return partner;
    }
  }
}
