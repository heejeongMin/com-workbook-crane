package com.workbook.crane.user.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 토큰 요청에 따른 응답 담는 객체
 */
@Getter
@NoArgsConstructor
public class OAuthToken {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private int expires_in;
	private String scope;
	private int refresh_token_expires_in;
}
