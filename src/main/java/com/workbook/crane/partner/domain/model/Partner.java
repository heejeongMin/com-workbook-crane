package com.workbook.crane.partner.domain.model;

import com.workbook.crane.partner.application.model.command.PartnerCreateCommand;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.partner.application.dto.PartnerDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@Table(name = "partner")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Partner {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "partner_number")
  private String partnerNumber;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "ceo_name")
  private String ceoName;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "created_by")
  private Long createdBy;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "modified_at")
  private LocalDateTime modifiedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public static Partner create(PartnerCreateCommand command, Long createdBy, String partnerNumber) {
  	Partner partner = new Partner();
  	partner.partnerNumber = partnerNumber;
  	partner.companyName = command.getCompanyName();
  	partner.ceoName = command.getCeoName();
  	partner.phoneNumber = command.getPhoneNumber();
  	partner.createdBy = createdBy;
  	return partner;
	}

  public Partner updatePartner(PartnerDto dto) {
    if(StringUtils.isNotEmpty(dto.getCompanyName()) && !dto.getCompanyName().equals(companyName)) {
      this.companyName = dto.getCompanyName();
    }
    if(StringUtils.isNotEmpty(dto.getCeoName()) && !dto.getCeoName().equals(ceoName)) {
      this.ceoName = dto.getCeoName();
    }
    if(StringUtils.isNotEmpty(dto.getPhoneNumber()) && !dto.getPhoneNumber().equals(ceoName)) {
      this.phoneNumber = dto.getPhoneNumber();
    }
    if(dto.getCreatedBy() != null && !dto.getCreatedBy().equals(createdBy)) {
      this.createdBy = dto.getCreatedBy();
    }
    return this;
  }

  public PartnerDto toDto() {
    return null;
  }

  public void deletePartner() {
    this.deletedAt = LocalDateTime.now();
  }
}
