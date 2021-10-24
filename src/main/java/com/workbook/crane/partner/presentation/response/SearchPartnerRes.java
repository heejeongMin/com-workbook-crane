package com.workbook.crane.partner.presentation.response;

import java.util.List;

import com.workbook.crane.partner.application.dto.PartnerDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchPartnerRes {
	private final List<PartnerDto> partnerDto;
}
