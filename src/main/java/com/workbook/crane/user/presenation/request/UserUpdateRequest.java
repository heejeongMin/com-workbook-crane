package com.workbook.crane.user.presenation.request;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.user.application.dto.UserDto;
import com.workbook.crane.user.domain.model.WorkType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest extends BaseEntity<UserDto> {
	//private Long oauthId;
	private String name;
	private String birthdate;
	private String phoneNumber;
	private String address;
	private WorkType workType;
	//private LocalDateTime deletedAt;
	
	@Override
	public UserDto toDto() {
		return new UserDto().builder()
				.name(this.name)
				.birthdate(this.birthdate)
				.phoneNumber(this.phoneNumber)
				.address(this.address)
				.build();
	}
}