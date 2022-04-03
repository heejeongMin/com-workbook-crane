package com.workbook.crane.partner.application.service;


import com.workbook.crane.partner.application.model.info.PartnerCreateInfo;
import com.workbook.crane.partner.application.model.command.PartnerCreateCommand;
import com.workbook.crane.partner.application.model.info.PartnerSearchInfo;
import com.workbook.crane.partner.domain.model.PartnerNumberTracker;
import com.workbook.crane.partner.domain.repository.PartnerNumberTrackerRepository;
import com.workbook.crane.user.application.service.AuthService;
import com.workbook.crane.user.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workbook.crane.partner.application.dto.PartnerDto;
import com.workbook.crane.partner.domain.model.Partner;
import com.workbook.crane.partner.domain.repository.PartnerRepository;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerService {

  private final AuthService authService;
  private final PartnerRepository partnerRepository;
  private final PartnerNumberTrackerRepository partnerNumberTrackerRepository;

  @Transactional(readOnly = true)
  public List<PartnerSearchInfo> searchPartnerAll(long userId, int page, int size) {
    return partnerRepository.findAllActivePartner(userId, PageRequest.of(page, size))
        .stream()
        .map(PartnerSearchInfo::from)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public PartnerSearchInfo searchPartnerByPartnerNumber(String partnerNumber, String userName)
      throws Exception {
    Partner partner =
        partnerRepository.findByPartnerNumberAndCreatedByAndDeletedAtIsNull(
            partnerNumber, authService.getUserOrElseThrow(userName).getId());
    return PartnerSearchInfo.from(partner);
  }

  @Transactional
  public PartnerCreateInfo createPartner(PartnerCreateCommand command, String username)
      throws Exception {
    User user = authService.getUserOrElseThrow(username);
    if (isExistingPartner(command, user.getId())) {
      throw new Exception("이미 존재하는 거래처입니다.");
    }

    Partner partner = Partner.create(command, user.getId(), this.generatePartnerNumber());
    partnerRepository.save(partner);
    return PartnerCreateInfo.from(partner);
  }

  private boolean isExistingPartner(PartnerCreateCommand command, Long userId) {
    Optional<Partner> partner =
        partnerRepository.findByCompanyNameContainsAndCreatedByAndDeletedAtIsNull(
            command.getCompanyName(), userId);

    return partner.isPresent();
  }

  private String generatePartnerNumber() {
    PartnerNumberTracker partnerNumberTracker =
        partnerNumberTrackerRepository.findById((long) ((Math.random() * 10) % 5) + 1).get();
    partnerNumberTracker.increaseIdentifierSeq();

    return partnerNumberTracker.getIdentifier() +
        StringUtils.leftPad(String.valueOf(partnerNumberTracker.getSeq()), 3, "0");
  }

//  @Transactional
//  public PartnerDto updatePartner(PartnerDto partnerDto) throws Exception {
//    Partner partner =
//        partnerRepository.findByIdAndDeletedAtIsNull(partnerDto.getId());
//
//    if (partner == null) {
//      throw new Exception("존재하지 않는 거래처입니다.");
//    }
//
//    partner.updatePartner(partnerDto);
//
//    return partner.toDto();
//  }

  @Transactional
  public PartnerDto deletePartner(Long id) {
    Optional<Partner> partner = partnerRepository.findById(id);
    if (!partner.isEmpty()) {
      partner.get().deletePartner();
    }
    return (partner.isEmpty()) ? new PartnerDto() : partner.get().toDto();
  }

  public Partner getPartnerOrElseThrow(Long id) throws Exception {
    Partner partner = partnerRepository.findByIdAndDeletedAtIsNull(id);
    if (partner == null) {
      throw new Exception("Partner does not exist");
    }
    return partner;
  }

  public Optional<Partner> isExistingPartnerNameByUser(String partnerName, Long userId) throws Exception {
    Optional<Partner> partner =
        partnerRepository.findByCompanyNameContainsAndCreatedByAndDeletedAtIsNull(
            partnerName, userId);
    if (partner.isEmpty()) {
      log.info("존재하지 않는 사명입니다. [partnerName : {}]", partnerName);
    }
    return partner;
  }
}
