package com.workbook.crane.user.application.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.user.domain.entity.User;
import com.workbook.crane.user.domain.entity.WorkType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto extends BaseDto<User> {
	private Long oauthId;
	private String name;
	private String birthdate;
	private String phoneNumber;
	private String address;
	private String nationality;
	private WorkType workType;
	private LocalDateTime deletedAt;

	@Builder
	public UserDto(Long oauthId, String name, String birthdate, String phoneNumber, String address, String nationality, WorkType workType, LocalDateTime deletedAt) {
		this.oauthId = oauthId;
		this.name = name;
		this.birthdate = birthdate;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.nationality = nationality;
		this.workType = workType;
		this.deletedAt = deletedAt;
	}

	@Override
	public User toEntity() {
		return new User().builder()
				.oauthId(oauthId)
				.name(name)
				.birthdate(birthdate)
				.phoneNumber(phoneNumber)
				.address(address)
				.nationality(nationality)
				.workType(workType)
				.build();
	}
	
}