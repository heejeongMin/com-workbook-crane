package com.workbook.crane.user.presenation.response;

import com.workbook.crane.user.application.dto.UserDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponse {
	private final UserDto dto;
}
