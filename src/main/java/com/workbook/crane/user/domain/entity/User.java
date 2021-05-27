package com.workbook.crane.user.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.user.application.dto.UserDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity<UserDto> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "oauth_id")
	private Long oauthId;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "work_type")
	private WorkType workType;
	
	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public User(Long oauthId, String name, String birthdate, String phoneNumber, String address, String nationality, WorkType workType, LocalDateTime deletedAt) {
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
	public UserDto toDto() {
		return new UserDto().builder()
				.oauthId(this.oauthId)
				.name(this.name)
				.birthdate(this.birthdate)
				.phoneNumber(this.phoneNumber)
				.address(this.address)
				.nationality(this.nationality)
				.workType(this.workType)
				.deletedAt(this.deletedAt)
				.build();
	}
	
	public void updateName(String name) {
		this.name = name;
	}

	public void updateBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public void updatePhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void updateAddress(String address) {
		this.address = address;
	}

	public void updateNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public void deleteUser(){
		this.deletedAt = LocalDateTime.now();
	}
}
