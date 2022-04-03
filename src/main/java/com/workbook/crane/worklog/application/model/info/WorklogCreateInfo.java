package com.workbook.crane.worklog.application.model.info;

import com.workbook.crane.worklog.domain.model.Worklog;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WorklogCreateInfo {

  private Long id;
  private Long equipmentId;
  private String location;
  private WorkPeriodInfo workPeriodInfo;
  private Long partnerId;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;

  public static WorklogCreateInfo from(Worklog worklog) {
    WorklogCreateInfo info = new WorklogCreateInfo();
    info.id = worklog.getId();
    info.equipmentId = worklog.getEquipment().getId();
    info.location = worklog.getLocation();
    info.workPeriodInfo = WorkPeriodInfo.from(worklog.getWorkPeriod());
    info.partnerId = worklog.getPartner().getId();
    info.createdAt = worklog.getCreatedAt();
    info.deletedAt = worklog.getDeletedAt();
    return info;
  }
}
