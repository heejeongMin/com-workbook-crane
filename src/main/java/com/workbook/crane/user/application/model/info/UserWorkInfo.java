package com.workbook.crane.user.application.model.info;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserWorkInfo {

  private final int numberOfHeavyEquipment;
  private final int numberOfPartners;
  private final int workDaysOfTheMonth;
  private final double workPayOfTheMonth;
  private final int workDaysOfLastMonth;
  private final double workPayOfLastMonth;
}
