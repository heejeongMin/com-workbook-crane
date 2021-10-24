package com.workbook.crane.partner.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workbook.crane.partner.application.dto.PartnerDto;
import com.workbook.crane.partner.domain.model.Partner;
import com.workbook.crane.partner.domain.repository.PartnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartnerService {

	private final PartnerRepository partnerRepository;
	
	@Transactional(readOnly = true)
	public List<PartnerDto> searchPartnerAll(int page, int size) {
		return partnerRepository
            .findAll(PageRequest.of(page, size))
            .stream()
            .map(Partner::toDto)
            .collect(Collectors.toList());
	}
	
	/*@Transactional(readOnly = true)
	public PartnerDto searchPartnerById(Long id) {
		Optional<Partner> partner = partnerRepository.findById(id);
		return (partner.isEmpty()) ? new PartnerDto() : partner.get().toDto();
	}*/
	
	@Transactional(readOnly = true)
	public PartnerDto searchPartnerByUserId(Long userId) {
		Optional<Partner> partner = partnerRepository.findByUserId(userId);
		return (partner.isEmpty()) ? new PartnerDto() : partner.get().toDto();
	}
	
	@Transactional(readOnly = true)
	public PartnerDto searchPartnerByPartnerNumber(String partnerNumber) {
		Optional<Partner> partner = partnerRepository.findByPartnerNumber(partnerNumber);
		return (partner.isEmpty()) ? new PartnerDto() : partner.get().toDto();
	}
	
	@Transactional
	public PartnerDto createPartner(PartnerDto partnerDto) {
		Partner partner = partnerRepository.save(partnerDto.toEntity());
		return partner.toDto();
	}
	
	@Transactional
	public PartnerDto updatePartner(PartnerDto partnerDto) {
		//Optional<Partner> updatePartner = partnerRepository.findById(partnerDto.getId());
		Optional<Partner> updatePartner = partnerRepository.findByPartnerNumber(partnerDto.getPartnerNumber());
		
		updatePartner.ifPresent(partner -> {
			partner.updatePartner(
					partnerDto.getCompanyName(), 
					partnerDto.getCeoName(), 
					partnerDto.getPhoneNumber());
		});
		
		return (updatePartner.isEmpty()) ? new PartnerDto() : updatePartner.get().toDto();
	}
	
	/*@Transactional
	public PartnerDto deletePartner(Long id) {
		Optional<Partner> partner = partnerRepository.findById(id);
		if (!partner.isEmpty()) {
			partner.get().deletePartner();
		}
		return (partner.isEmpty()) ? new PartnerDto() : partner.get().toDto();
	}*/
	
	@Transactional
	public PartnerDto deletePartner(String partnerNumber) {
		Optional<Partner> partner = partnerRepository.findByPartnerNumber(partnerNumber);
		if (!partner.isEmpty()) {
			partner.get().deletePartner();
		}
		return (partner.isEmpty()) ? new PartnerDto() : partner.get().toDto();
	}
	
}
