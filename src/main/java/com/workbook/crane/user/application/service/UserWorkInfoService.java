package com.workbook.crane.user.application.service;

import com.workbook.crane.partner.domain.repository.PartnerRepository;
import com.workbook.crane.user.application.model.info.UserWorkInfo;
import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.worklog.domain.repository.HeavyEquipmentRepository;
import com.workbook.crane.worklog.domain.repository.WorklogRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserWorkInfoService {

  private final AuthService authService;
  private final HeavyEquipmentRepository heavyEquipmentRepository;
  private final PartnerRepository partnerRepository;
  private final WorklogRepository worklogRepository;

  public UserWorkInfo getUserWorkInfo(String username) throws Exception {
    User user = authService.getUserOrElseThrow(username);
    int numberOfHeayEquipment = heavyEquipmentRepository.countAllByUserAndDeletedAtIsNull(user);
    int numberOfPartners = partnerRepository.countAllByCreatedByAndDeletedAtIsNull(user.getId());

    LocalDate now = LocalDate.now();
    LocalDate startDayOfLastMonth = now.minusMonths(1L).withDayOfMonth(1);
    LocalDate startDayOfTheMonth = now.withDayOfMonth(1);
    LocalDate startDayOfNextMonth = now.plusMonths(1L).withDayOfMonth(1);
    int workDaysOfTheMonth = worklogRepository.countAllByUserAndDeletedAtIsNullAndWorkDateIsBetween(
        user, startDayOfTheMonth, startDayOfNextMonth);
    double workPayOfTheMonth = worklogRepository.sumWorkPayByUserAndDeletedAtIsNullAndWorkDateIsBetween(
        startDayOfTheMonth, startDayOfNextMonth, user);

    int workDaysOfLastMonth = worklogRepository.countAllByUserAndDeletedAtIsNullAndWorkDateIsBetween(
        user, startDayOfLastMonth, startDayOfTheMonth);
    double workPayOfLastMonth = worklogRepository.sumWorkPayByUserAndDeletedAtIsNullAndWorkDateIsBetween(
        startDayOfLastMonth, startDayOfTheMonth, user);

    return new UserWorkInfo(
        numberOfHeayEquipment,
        numberOfPartners,
        workDaysOfTheMonth,
        workPayOfTheMonth,
        workDaysOfLastMonth,
        workPayOfLastMonth);
  }
}
