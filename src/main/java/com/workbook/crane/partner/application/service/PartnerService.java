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
    return partnerRepository.findAllActivePartner(PageRequest.of(page, size))
        .stream()
        .map(Partner::toDto)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public PartnerDto searchPartnerById(Long partnerId) {
    Partner partner = partnerRepository.findByIdAndDeletedAtIsNull(partnerId);
    return (partner == null) ? new PartnerDto() : partner.toDto();
  }

  @Transactional
  public PartnerDto createPartner(PartnerDto partnerDto) throws Exception {
    if (null !=
        partnerRepository.findByCompanyNameAndDeletedAtIsNull(partnerDto.getCompanyName())) {
      throw new Exception("존재하는 사명입니다.");
    }

    Partner partner = partnerRepository.findTheNewestCreatedPartner();
    String partnerNumber = partner == null
        ? "p001" : partner.generatePartnerNumber(partner.getPartnerNumber());
    partnerDto.setPartnerNumber(partnerNumber);
    return partnerRepository.save(Partner.create(partnerDto)).toDto();
  }

  @Transactional
  public PartnerDto updatePartner(PartnerDto partnerDto) throws Exception {
    Partner partner =
        partnerRepository.findByIdAndDeletedAtIsNull(partnerDto.getId());

    if (partner == null) {
      throw new Exception("존재하지 않는 거래처입니다.");
    }

    partner.updatePartner(partnerDto);

    return partner.toDto();
  }

  @Transactional
  public PartnerDto deletePartner(Long id) {
    Optional<Partner> partner = partnerRepository.findById(id);
    if (!partner.isEmpty()) {
      partner.get().deletePartner();
    }
    return (partner.isEmpty()) ? new PartnerDto() : partner.get().toDto();
  }

}
