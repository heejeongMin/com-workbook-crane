package com.workbook.crane.user.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoProfile {
	private Long id;
	private String connected_at;
	private Properties properties;
	private KakaoAccount kakao_account;
}