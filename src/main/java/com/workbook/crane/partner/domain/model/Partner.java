package com.workbook.crane.partner.domain.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.partner.application.dto.PartnerDto;
import com.workbook.crane.user.application.dto.UserDto;
import com.workbook.crane.user.domain.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "partner")
@Entity
public class Partner extends BaseEntity<PartnerDto> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "partner_number")
	private String partnerNumber;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "ceo_name")
	private String ceoName;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public Partner(Long id, User user, String partnerNumber, String companyName, String ceoName, String phoneNumber, LocalDateTime deletedAt) {
		this.id = id;
		this.user = user;
		this.partnerNumber = partnerNumber;
		this.companyName = companyName;
		this.ceoName = ceoName;
		this.phoneNumber = phoneNumber;
		this.deletedAt = deletedAt;
	}

	public Partner updatePartner(String companyName, String ceoName, String phoneNumber) {
		this.companyName = companyName;
		this.ceoName = ceoName;
		this.phoneNumber = phoneNumber;
		return this;
	}

	@Override
	public PartnerDto toDto() {
		PartnerDto partnerDto = new PartnerDto().builder()
												.id(id)
												.userDto(user.toDto())
												.partnerNumber(partnerNumber)
												.companyName(companyName)
												.ceoName(ceoName)
												.phoneNumber(phoneNumber)
												.deletedAt(deletedAt)
												.build();
		
		return partnerDto;
	}
	
	public void deletePartner(){
		this.deletedAt = LocalDateTime.now();
	}
}
