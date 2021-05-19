package com.workbook.crane.worklogContext.application.Dto;

import com.workbook.crane.worklogContext.domain.model.WorkTimeUnit;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WorkPeriodDto {

  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private WorkTimeUnit workTimeUnit;
  private long workhours;
}
