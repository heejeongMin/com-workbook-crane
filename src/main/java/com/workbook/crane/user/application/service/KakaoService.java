package com.workbook.crane.user.application.service;

import com.workbook.crane.user.application.dto.UserDto;
import com.workbook.crane.user.domain.entity.KakaoProfile;
import com.workbook.crane.user.domain.entity.OAuthToken;
import com.workbook.crane.user.domain.entity.User;
import com.workbook.crane.user.domain.repository.UserRepository;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workbook.crane.user.domain.entity.WorkType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
	
	@Value("${kakao.client-id}")
	private String CLIENT_ID;
	
	//private final UserService userService;
	private final UserRepository userRepository;

	public void kakaoConnect(HttpServletResponse httpServletResponse) throws IOException {
		StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id="+CLIENT_ID);
        url.append("&redirect_uri=http://localhost:8080/oauth/callback");
        url.append("&response_type=code");

        httpServletResponse.sendRedirect(url.toString());
	}
	
	public UserDto kakaoCallback(String code) {
		// -----------------------------토큰 받기----------------------------
		// Post방식으로 key=value 데이터를 요청(카카오쪽으로) 
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성 
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", CLIENT_ID);
		params.add("redirect_uri", "http://localhost:8080/oauth/callback");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기 
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		
		// Post 방식으로 Http 요청하기 - response 변수에 응답 받음. 
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",	// 토큰 발급 요청 주소
				HttpMethod.POST,						// 요청 메서드
				kakaoTokenRequest,						// 데이터와 httpHeader
				String.class							// 응답받을 타입 
		);
		
		
		// -----------------------------토큰 정보 보기-----------------------------
		// 응답받은 JSON 데이터를 Object에 담음.
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
		// -----------------------------토큰을 통한 사용자 정보 조회(POST)-----------------------------
		// Post방식으로 key=value 데이터를 요청(카카오쪽으로) 
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기 
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		
		// Post 방식으로 Http 요청하기 - response 변수에 응답 받음. 
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",	// 토큰 정보 보기 요청 주소
				HttpMethod.POST,						// 요청 메서드
				kakaoProfileRequest,					// 데이터와 httpHeader
				String.class							// 응답받을 타입 
		);
		
		
		// -----------------------------토큰을 통한 사용자 정보 보기-----------------------------
		// 응답받은 JSON 데이터를 Object에 담음.
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		System.out.println("두루미 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());


		// -----------------------------User객체에 사용자 정보 담기-----------------------------
		UserDto userDto = new UserDto().builder()
				.oauthId(kakaoProfile.getId())
				.name(kakaoProfile.getProperties().getNickname())
				.birthdate("")
				.phoneNumber("")
				.address("")
				.nationality("")
				.workType(WorkType.EMPLOYED)
				.build();
		
		
		// -----------------------------DB에 저장된 값이 있으면 로그인, 없으면 회원가입-----------------------------
		Long checkOauthId = userDto.getOauthId();
		//UserDto searchUser = userService.searchUserById(checkOauthId);
		Optional<User> searchUser = userRepository.findByOauthId(checkOauthId);
		
		if (searchUser.isEmpty()) {		// 회원가입 
			//userService.createUser(userDto);
			userRepository.save(userDto.toEntity());
		}
		
		return userDto;
	}
}
