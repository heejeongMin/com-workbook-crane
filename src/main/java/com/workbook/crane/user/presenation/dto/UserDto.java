package com.workbook.crane.user.presenation.dto;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto extends BaseDto<User> {

	//@Column(name = "name")
	private String name;

	//@Column(name = "birthdate")
	private String birthdate;
	
	//@Column(name = "phone_number")
	private String phoneNumber;
	
	//@Column(name = "address")
	private String address;
	
	//@Column(name = "nationality")
	private String nationality;
	
	//@Column(name = "work_type")
	private String workType;

	@Builder
	public UserDto(String name, String birthdate, String phoneNumber, String address, String nationality, String workType) {
		this.name = name;
		this.birthdate = birthdate;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.nationality = nationality;
		this.workType = workType;
	}

	@Override
	public User toEntity() {
		return new User().builder()
				.name(this.name)
				.birthdate(this.birthdate)
				.phoneNumber(this.phoneNumber)
				.address(this.address)
				.nationality(this.nationality)
				.workType(this.workType)
				.build();
	}
	
}