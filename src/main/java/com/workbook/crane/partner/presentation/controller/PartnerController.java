package com.workbook.crane.partner.presentation.controller;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workbook.crane.partner.application.service.PartnerService;
import com.workbook.crane.partner.presentation.request.PartnerReq;
import com.workbook.crane.partner.presentation.response.PartnerRes;
import com.workbook.crane.partner.presentation.response.SearchPartnerRes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PartnerController {
	
	private final PartnerService partnerService;
	
	@GetMapping(value = "/crane/v1/partner")
	public ResponseEntity<SearchPartnerRes> searchAllPartner(
		    @RequestParam(value =  "page", defaultValue = "1") int page,
		    @RequestParam(value =  "size", defaultValue = "1") int size){
		return ResponseEntity.ok(new SearchPartnerRes(partnerService.searchPartnerAll(page, size)));
	}
	
	/*@GetMapping(value = "/crane/v1/partner/{id}")
	public ResponseEntity<PartnerRes> searchPartnerById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(new PartnerRes(Arrays.asList(partnerService.searchPartnerById(id))));
	}*/
	
	@GetMapping(value = "/crane/v1/partners/{userId}")
	public ResponseEntity<SearchPartnerRes> searchPartnerByUserId(@PathVariable(value = "userId") Long userId) {
		return ResponseEntity.ok(new SearchPartnerRes(Arrays.asList(partnerService.searchPartnerByUserId(userId))));
	}
	
	@GetMapping(value = "/crane/v1/partner/{partnerNumber}")
	public ResponseEntity<SearchPartnerRes> searchPartnerByPartnerNumber(@PathVariable(value = "partnerNumber") String partnerNumber) {
		return ResponseEntity.ok(new SearchPartnerRes(Arrays.asList(partnerService.searchPartnerByPartnerNumber(partnerNumber))));
	}
	
	@PostMapping(value = "/crane/v1/partner")
	public ResponseEntity<PartnerRes> createPartner(@RequestBody PartnerReq partnerReq) {
		return ResponseEntity.ok(new PartnerRes(partnerService.createPartner(partnerReq.toDto())));
	}

	/*@PatchMapping(value = "/crane/v1/partner/{id}")
	public ResponseEntity<PartnerRes> updatePartner(@RequestBody PartnerReq partnerReq) {
		return ResponseEntity.ok(new PartnerRes(Arrays.asList(partnerService.updatePartner(partnerReq.toDto()))));
	}*/
	
	@PatchMapping(value = "/crane/v1/partner/{partnerNumber}")
	public ResponseEntity<PartnerRes> updatePartner(@PathVariable(value = "partnerNumber") String partnerNumber, @RequestBody PartnerReq partnerReq) {
		return ResponseEntity.ok(new PartnerRes(partnerService.updatePartner(partnerReq.toDto())));
	}
	
	/*@DeleteMapping(value = "/crane/v1/partner/{id}")
	public ResponseEntity<PartnerRes> deletePartner(@PathVariable(value = "id") Long id) {
	    return ResponseEntity.ok(new PartnerRes(partnerService.deletePartner(id)));
	}*/
	
	@DeleteMapping(value = "/crane/v1/partner/{partnerNumber}")
	public ResponseEntity<PartnerRes> deletePartner(@PathVariable(value = "partnerNumber") String partnerNumber) {
	    return ResponseEntity.ok(new PartnerRes(partnerService.deletePartner(partnerNumber)));
	}
	
}
