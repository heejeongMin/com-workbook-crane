package com.workbook.crane.user.presenation.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequest {
	private final String name;
	private final String birthdate;
	private final String phoneNumber;
	private final String address;
	private final String nationality;
	private final String workType;
	
}