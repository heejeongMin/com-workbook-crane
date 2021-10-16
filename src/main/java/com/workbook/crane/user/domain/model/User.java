package com.workbook.crane.user.domain.model;

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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity<UserDto> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
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

	//@Column(name = "nationality")
	//private String nationality;

	@Enumerated(EnumType.STRING)
	@Column(name = "work_type")
	private WorkType workType;
	
	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public User(Long id, Long oauthId, String name, String birthdate, String phoneNumber, String address, WorkType workType, LocalDateTime deletedAt) {
		this.id = id;
		this.oauthId = oauthId;
		this.name = name;
		this.birthdate = birthdate;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.workType = workType;
		this.deletedAt = deletedAt;
	}

	public User updateUser(String name, String birthdate, String phoneNumber, String address) {
		this.name = name;
		this.birthdate = birthdate;
		this.phoneNumber = phoneNumber;
		this.address = address;
		return this;
	}
	
	@Override
	public UserDto toDto() {
		return UserDto.builder()
				.id(this.id)
				.oauthId(this.oauthId)
				.name(this.name)
				.birthdate(this.birthdate)
				.phoneNumber(this.phoneNumber)
				.address(this.address)
				.workType(workType)
				.deletedAt(this.deletedAt)
				.build();
	}

	public void deleteUser(){
		this.deletedAt = LocalDateTime.now();
	}
}
