package com.workbook.crane.worklog.application.Dto;

import com.workbook.crane.worklog.application.model.info.WorkPeriodInfo;
import com.workbook.crane.worklog.domain.model.WorkPeriod;
import com.workbook.crane.worklog.domain.model.WorkTimeUnit;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WorkPeriodDto {
  private LocalDateTime startedAt;
  private LocalDateTime finishedAt;

  public static WorkPeriodDto from(WorkPeriodInfo info){
    WorkPeriodDto dto = new WorkPeriodDto();
    dto.startedAt = info.getStartedAt();
    dto.finishedAt = info.getFinishedAt();
    return dto;
  }
}
