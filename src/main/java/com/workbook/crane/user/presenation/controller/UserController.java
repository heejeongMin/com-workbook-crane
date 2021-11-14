package com.workbook.crane.user.presenation.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.workbook.crane.user.application.service.KakaoService;
import com.workbook.crane.user.application.service.UserService;
import com.workbook.crane.user.presenation.request.UserUpdateRequest;
import com.workbook.crane.user.presenation.response.UserRes;

import lombok.RequiredArgsConstructor;

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
	public ResponseEntity<UserRes> kakaoCallback(String code) {
		UserRes userRes = new UserRes(kakaoService.kakaoCallback(code));
		return ResponseEntity.ok(userRes);
	}

	@GetMapping(value = "/crane/v1/user/{oauthId}")
	public ResponseEntity<UserRes> searchUser(@PathVariable(value = "oauthId") Long oauthId) {
		return ResponseEntity.ok(new UserRes(userService.searchUser(oauthId)));
	}
	
	@PatchMapping(value = "/crane/v1/user/{oauthId}")
 	public ResponseEntity<UserRes> updateUser(@PathVariable(value = "oauthId") Long oauthId, @RequestBody UserUpdateRequest userUpdateRequest) {
		return ResponseEntity.ok(new UserRes(userService.updateUser(oauthId, userUpdateRequest.toDto())));
	}
	
	@DeleteMapping(value = "/crane/v1/user/{oauthId}")
	public ResponseEntity<UserRes> deleteUser(@PathVariable(value = "oauthId") Long oauthId) {
	    return ResponseEntity.ok(new UserRes(userService.deleteUser(oauthId)));
	}
	
}