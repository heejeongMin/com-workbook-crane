package com.workbook.crane.user.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Profile {
	private String nickname;
	private Boolean is_default_image;
	private String thumbnail_image_url;
	private String profile_image_url;
}