package com.workbook.crane.user.presenation.response;

import com.workbook.crane.common.BaseResponse;
import com.workbook.crane.user.application.dto.UserDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRes extends BaseResponse {
	private final UserDto userDto;
}
