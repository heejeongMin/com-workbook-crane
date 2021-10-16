package com.workbook.crane.partner.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	private UserDto userDto;
	private String partnerNumber;
	private String companyName;
	private String ceoName;
	private String phoneNumber;
	private LocalDateTime deletedAt;
	
	@Builder
	public PartnerDto(Long id, UserDto userDto, String partnerNumber, String companyName, String ceoName, String phoneNumber, LocalDateTime deletedAt) {
		this.id = id;
		this.userDto = userDto;
		this.partnerNumber = partnerNumber;
		this.companyName = companyName;
		this.ceoName = ceoName;
		this.phoneNumber = phoneNumber;
		this.deletedAt = deletedAt;
	}
	
	@Override
	public Partner toEntity() {
		return Partner.builder()
				.user(userDto.toEntity())
				.partnerNumber(partnerNumber)
				.companyName(companyName)
				.ceoName(ceoName)
				.phoneNumber(phoneNumber)
				.build();
	}

	public void setPartnerNumber(String format) {
		StringBuilder st = new StringBuilder("p");
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
		st.append(today.format(formatter));
		st.append("-");
		
		this.partnerNumber = st.toString();
	}

}
