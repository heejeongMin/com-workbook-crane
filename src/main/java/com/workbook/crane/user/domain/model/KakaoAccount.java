package com.workbook.crane.user.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoAccount {
	private Boolean profile_needs_agreement;
	private Profile profile;
	private Boolean has_email;
	private Boolean email_needs_agreement;
	private Boolean is_email_valid;
	private Boolean is_email_verified;
	private String email;
	private Boolean has_birthday;
	private Boolean birthday_needs_agreement;
}