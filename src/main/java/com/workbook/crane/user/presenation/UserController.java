package com.workbook.crane.user.presenation;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workbook.crane.user.application.UserService;
import com.workbook.crane.user.domain.KakaoProfile;
import com.workbook.crane.user.domain.OAuthToken;
import com.workbook.crane.user.presenation.dto.UserDto;
import com.workbook.crane.user.presenation.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/user")
public class UserController {
	
	public final UserService userService;
	
	@Value("${kakao.client-id}")
	private String CLIENT_ID;
	
	@ResponseBody
	@GetMapping(value="/user/{id}")
	public ResponseEntity<UserResponse> searchUser(@PathVariable Long id) {
		UserDto userDto = userService.searchUser(id);
		return ResponseEntity.ok(new UserResponse(userDto));
	}
	
	@ResponseBody
	@GetMapping(value="/oauth")
	public void kakaoConnect(HttpServletResponse httpServletResponse) throws IOException {
		StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id="+CLIENT_ID);
        url.append("&redirect_uri=http://localhost:8080/oauth/callback");
        url.append("&response_type=code");

        httpServletResponse.sendRedirect(url.toString());
	}
	
	@GetMapping("/oauth/callback")
	//public @ResponseBody String kakaoCallbak(String code) {
	public @ResponseBody ResponseEntity<UserResponse> kakaoCallbak(String code) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(oauthToken.getAccess_token());
		
		
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
		
		System.out.println(response2.getBody());
		
		
		// -----------------------------토큰을 통한 사용자 정보 보기-----------------------------
		// 응답받은 JSON 데이터를 Object에 담음.
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		System.out.println("두루미 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());

		
		// -----------------------------User객체에 사용자 정보 담고 DB에 저장-----------------------------
		UserDto userDto = new UserDto().builder()
				.name(kakaoProfile.getProperties().getNickname() +"_"+ kakaoProfile.getId())
				.birthdate("")
				.phoneNumber("")
				.address("")
				.nationality("")
				.workType("employed")
				.build();
		
		//return response2.getBody();
		return ResponseEntity.ok(new UserResponse(userService.saveUser(userDto)));
	}
	
	@GetMapping(value = "/greeting")
	public String greeting(@RequestParam(value = "name", required = false, defaultValue = "파라미터 입력") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }
	
	//@PostMapping    // POST 요청 방식의 API
    //public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) { // @RequestBody : 클라이언트가 전송하는 Http 요청의 Body내용을 Java Object로 변환시켜주는 역할

        // 회원이 등록되어 있는지 체크
        /*UserDto userDto = userService.searchUser(userRequest.toEntity().getId());
        if(userDto != null) {  // 등록되어 있다면
            return ResponseEntity.ok(new UserResponse(userService.searchUser(userRequest.toEntity().getId())));
        }
        // 등록되어 있지 않다면(db에 저장)
        // 응답 헤더의 상태 코드 본문을 직접 다루기 위해 사용
        return ResponseEntity.ok(   // 200 ok 상태코드
                new UserResponse(userService.createUser(userRequest.toEntity()))
        );*/

    //}
	
	
	/*@GetMapping(value = "/")
	public ResponseEntity<UserResponse> Userontroller(@ModelAttribute UserRequest req) {
		return ResponseEntity.ok(new UserResponse(userService.userService(
				new UserDto().builder()
						.name(req.getName())
						.birthdate(req.getBirthdate())
						.phoneNumber(req.getPhoneNumber())
						.address(req.getAddress())
						.nationality(req.getNationality())
						.workType(req.getWorkType())
						.build())));
	}*/
}