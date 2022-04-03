package com.workbook.crane.worklog.application.model.info;

import com.workbook.crane.worklog.domain.model.WorkPeriod;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WorkPeriodInfo {
  private LocalDateTime startedAt;
  private LocalDateTime finishedAt;

  public static WorkPeriodInfo from(WorkPeriod period){
    WorkPeriodInfo info = new WorkPeriodInfo();
    info.startedAt = period.getStartedAt();
    info.finishedAt = period.getFinishedAt();
    return info;
  }
}
