package com.workbook.crane.user.presenation.request;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.workbook.crane.user.domain.entity.WorkType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequest {
	private final Long oauthId;
	private final String name;
	private final String birthdate;
	private final String phoneNumber;
	private final String address;
	private final String nationality;
	@Enumerated(EnumType.STRING)
	private final WorkType workType;
	private final LocalDateTime deletedAt;
}