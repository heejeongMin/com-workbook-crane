package com.workbook.crane.partner.presentation.controller;

import com.workbook.crane.partner.application.dto.PartnerDto;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workbook.crane.partner.application.service.PartnerService;
import com.workbook.crane.partner.presentation.request.PartnerReq;
import com.workbook.crane.partner.presentation.response.PartnerRes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PartnerController {
	
	private final PartnerService partnerService;
	
	@GetMapping(value = "/crane/v1/partner")
	public ResponseEntity<PartnerRes> searchAllPartner(
		    @RequestParam(value =  "page", defaultValue = "1") int page,
		    @RequestParam(value =  "size", defaultValue = "10") int size){
		return ResponseEntity.ok(new PartnerRes(partnerService.searchPartnerAll(page, size)));
	}
	
	@GetMapping(value = "/crane/v1/partner/{partnerId}")
	public ResponseEntity<PartnerRes> searchPartnerByPartnerNumber(
			@PathVariable(value = "partnerId") Long partnerId) {
		return ResponseEntity.ok(
				new PartnerRes(Arrays.asList(partnerService.searchPartnerById(partnerId))));
	}
	
	@PostMapping(value = "/crane/v1/partner")
	public ResponseEntity<PartnerRes> createPartner(
			@RequestBody PartnerReq partnerReq) throws Exception {
		return ResponseEntity.ok
				(new PartnerRes(Arrays.asList(partnerService.createPartner(PartnerDto.from(partnerReq)))));
	}
	
	@PutMapping(value = "/crane/v1/partner/{partnerId}")
	public ResponseEntity<PartnerRes> updatePartner(
			@PathVariable(value = "partnerId") Long partnerId,
			@RequestBody PartnerReq partnerReq) throws Exception {
		return ResponseEntity.ok(
				new PartnerRes(Arrays.asList(
						partnerService.updatePartner(PartnerDto.of(partnerId, partnerReq)))));
	}

	@DeleteMapping(value = "/crane/v1/partner/{partnerId}")
	public ResponseEntity<PartnerRes> deletePartner(
			@PathVariable(value = "partnerId") Long partnerId) throws Exception {
		return ResponseEntity.ok(
				new PartnerRes(Arrays.asList(partnerService.deletePartner(partnerId))));
	}
	
}
