package com.workbook.crane.worklog.application.Dto;

import com.workbook.crane.worklog.domain.model.WorkTimeUnit;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WorkPeriodDto {

  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private WorkTimeUnit workTimeUnit;
  private long workhours;
}
