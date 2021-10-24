package com.workbook.crane.user.application.dto;

import java.time.LocalDateTime;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.user.domain.model.WorkType;

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