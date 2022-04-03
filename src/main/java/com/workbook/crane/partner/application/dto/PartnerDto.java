package com.workbook.crane.partner.application.dto;

import java.time.LocalDateTime;

import com.workbook.crane.partner.domain.model.Partner;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PartnerDto {

  private Long id;
  private String partnerNumber;
  private String companyName;
  private String ceoName;
  private String phoneNumber;
  private Long createdBy;
  private LocalDateTime deletedAt;


  public Partner toEntity() {
    return null;
  }

  public void setPartnerNumber(String partnerNumber){
    this.partnerNumber = partnerNumber;
  }
}
