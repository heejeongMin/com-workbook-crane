package com.workbook.crane.user.presenation.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.workbook.crane.user.application.dto.UserDto;
import com.workbook.crane.user.application.service.KakaoService;
import com.workbook.crane.user.application.service.UserService;
import com.workbook.crane.user.presenation.request.UserUpdateRequest;
import com.workbook.crane.user.presenation.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
	
	public final UserService userService;
	public final KakaoService kakaoService;
	
	@GetMapping(value = "/oauth")
	public void kakaoConnect(HttpServletResponse httpServletResponse) throws IOException {
		kakaoService.kakaoConnect(httpServletResponse);
	}
	
	@GetMapping(value = "/oauth/callback")
	public ResponseEntity<UserResponse> kakaoCallback(String code) {
		UserDto userDto = kakaoService.kakaoCallback(code);
		return ResponseEntity.ok(new UserResponse(userDto));
	}

	@GetMapping(value = "/crane/v1/user/{oauthId}")
	public ResponseEntity<UserResponse> searchUser(@PathVariable(value = "oauthId") Long oauthId) {
		log.info("test");

		return ResponseEntity.ok(new UserResponse(userService.searchUser(oauthId)));
	}
	
	@PatchMapping(value = "/crane/v1/user/{oauthId}")
 	public ResponseEntity<UserResponse> updateUser(@PathVariable(value = "oauthId") Long oauthId, @RequestBody UserUpdateRequest userUpdateRequest) {
		return ResponseEntity.ok(new UserResponse(userService.updateUser(oauthId, userUpdateRequest.toDto())));
	}
	
	@DeleteMapping(value = "/crane/v1/user/{oauthId}")
	public ResponseEntity<UserResponse> deleteUser(@PathVariable(value = "oauthId") Long oauthId) {
	    return ResponseEntity.ok(new UserResponse(userService.deleteUser(oauthId)));
	}
	
}