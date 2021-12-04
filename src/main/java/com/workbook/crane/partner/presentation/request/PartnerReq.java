package com.workbook.crane.partner.presentation.request;

import com.workbook.crane.partner.application.dto.PartnerDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PartnerReq {

	private String companyName;
	private String ceoName;
	private String phoneNumber;
	private Long createdBy;

	public PartnerDto toDto() {
		PartnerDto partnerDto = new PartnerDto().builder()
								.companyName(companyName)
								.ceoName(ceoName)
								.phoneNumber(phoneNumber)
								.build();
		
		return partnerDto;
	}
}
