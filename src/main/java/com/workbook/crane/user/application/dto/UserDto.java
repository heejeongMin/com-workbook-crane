package com.workbook.crane.user.application.dto;

import java.time.LocalDateTime;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.user.domain.model.WorkType;
import com.workbook.crane.worklog.application.Dto.PriceDto;
import com.workbook.crane.worklog.domain.model.EquipmentType;
import com.workbook.crane.worklog.domain.model.EquipmentUnit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends BaseDto<User> {
	private Long id;
	private Long oauthId;
	private String name;
	private String birthdate;
	private String phoneNumber;
	private String address;
	private WorkType workType;
	private LocalDateTime deletedAt;

	/*@Builder
	public UserDto(Long id, Long oauthId, String name, String birthdate, String phoneNumber, String address, WorkType workType, LocalDateTime deletedAt) {
		this.id = id;
		this.oauthId = oauthId;
		this.name = name;
		this.birthdate = birthdate;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.workType = workType;
		this.deletedAt = deletedAt;
	}*/
	
	@Override
	public User toEntity() {
		return User.builder()
				.id(id)
				.oauthId(oauthId)
				.name(name)
				.birthdate(birthdate)
				.phoneNumber(phoneNumber)
				.address(address)
				.workType(workType)
				.build();
	}
	
}