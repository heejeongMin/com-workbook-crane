package com.workbook.crane.partner.presentation.response;

import com.workbook.crane.partner.application.dto.PartnerDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PartnerRes {
	private final PartnerDto partnerDto;
}
