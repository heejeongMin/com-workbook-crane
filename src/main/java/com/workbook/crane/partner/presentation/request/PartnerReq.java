package com.workbook.crane.partner.presentation.request;

import com.workbook.crane.partner.application.dto.PartnerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PartnerReq {
	
	private Long id;
	private String partnerNumber;
	private String companyName;
	private String ceoName;
	private String phoneNumber;
	private String deletedAt;

	public PartnerDto toDto() {
		PartnerDto partnerDto = new PartnerDto().builder()
								.id(id)
								.partnerNumber(partnerNumber)
								.companyName(companyName)
								.ceoName(ceoName)
								.phoneNumber(phoneNumber)
								.build();
		
		return partnerDto;
	}
}
