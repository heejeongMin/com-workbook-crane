package com.workbook.crane.user.presenation.model.response;

import com.workbook.crane.user.application.model.info.UserWorkInfo;
import lombok.Getter;

@Getter
public class UserWorkInfoResponse {

  private int numberOfHeavyEquipment;
  private int numberOfPartners;
  private int workDaysOfTheMonth;
  private double workPayOfTheMonth;
  private int workDaysOfLastMonth;
  private double workPayOfLastMonth;

  public static UserWorkInfoResponse of(UserWorkInfo userWorkInfo) {
    UserWorkInfoResponse response = new UserWorkInfoResponse();
    response.numberOfHeavyEquipment = userWorkInfo.getNumberOfHeavyEquipment();
    response.numberOfPartners = userWorkInfo.getNumberOfPartners();
    response.workDaysOfTheMonth = userWorkInfo.getWorkDaysOfTheMonth();
    response.workPayOfTheMonth = userWorkInfo.getWorkPayOfTheMonth();
    response.workDaysOfLastMonth = userWorkInfo.getWorkDaysOfLastMonth();
    response.workPayOfLastMonth = userWorkInfo.getWorkPayOfLastMonth();
    return response;
  }
}
