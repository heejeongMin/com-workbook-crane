package com.workbook.crane.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.user.presenation.dto.UserDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity<UserDto> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "birthdate")
	private String birthdate;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "address")
	private String address;

	@Column(name = "nationality")
	private String nationality;

	@Column(name = "work_type")
	private String workType;

	@Builder
	public User(String name, String birthdate, String phoneNumber, String address, String nationality,
			String workType) {
		this.name = name;
		this.birthdate = birthdate;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.nationality = nationality;
		this.workType = workType;
	}

	@Override
	public UserDto toDto() {
		return new UserDto().builder()
				.name(this.name)
				.birthdate(this.birthdate)
				.phoneNumber(this.phoneNumber)
				.address(this.address)
				.nationality(this.nationality)
				.workType(this.workType)
				.build();
	}
}
