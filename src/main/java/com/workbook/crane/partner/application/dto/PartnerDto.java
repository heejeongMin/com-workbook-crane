package com.workbook.crane.partner.application.dto;

import com.workbook.crane.partner.presentation.request.PartnerReq;
import java.time.LocalDateTime;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.partner.domain.model.Partner;
import com.workbook.crane.user.application.dto.UserDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PartnerDto extends BaseDto<Partner> {

  private Long id;
  private String partnerNumber;
  private String companyName;
  private String ceoName;
  private String phoneNumber;
  private Long createdBy;
  private LocalDateTime deletedAt;

  public static PartnerDto from(PartnerReq req) {
    PartnerDto partnerDto = new PartnerDto();
    partnerDto.companyName = req.getCompanyName();
    partnerDto.ceoName = req.getCeoName();
    partnerDto.phoneNumber = req.getPhoneNumber();
    partnerDto.createdBy = null == req.getCreatedBy()? 1 : req.getCreatedBy();
    return partnerDto;
  }

  public static PartnerDto of(Long partnerId, PartnerReq req){
    PartnerDto partnerDto = new PartnerDto();
    partnerDto.id = partnerId;
    partnerDto.companyName = req.getCompanyName();
    partnerDto.ceoName = req.getCeoName();
    partnerDto.phoneNumber = req.getPhoneNumber();
    partnerDto.createdBy = req.getCreatedBy();
    return partnerDto;
  }

  @Builder
  public PartnerDto(Long id, UserDto userDto, String partnerNumber, String companyName,
      String ceoName, String phoneNumber, Long createdBy, LocalDateTime deletedAt) {
    this.id = id;
    this.partnerNumber = partnerNumber;
    this.companyName = companyName;
    this.ceoName = ceoName;
    this.phoneNumber = phoneNumber;
    this.createdBy = createdBy;
    this.deletedAt = deletedAt;
  }

  @Override
  public Partner toEntity() {
    return Partner.builder()
        .partnerNumber(partnerNumber)
        .companyName(companyName)
        .ceoName(ceoName)
        .phoneNumber(phoneNumber)
        .build();
  }

  public void setPartnerNumber(String partnerNumber){
    this.partnerNumber = partnerNumber;
  }
}
