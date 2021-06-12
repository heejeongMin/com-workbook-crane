package com.workbook.crane.worklog.domain.model;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.worklog.application.Dto.WorkPeriodDto;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class WorkPeriod extends BaseEntity<WorkPeriodDto> {

  @Column(name = "start_date")
  private LocalDateTime startDate;

  @Column(name = "end_date")
  private LocalDateTime endDate;

//  @Transient
//  private WorkTimeUnit workTimeUnit;
//
//  @Transient
//  private long workhours;

//  public static WorkPeriod of(LocalDateTime startDate, LocalDateTime endDate){

//    return new WorkPeriod(startDate, endDate,WorkTimeUnit.calculateWorkTimeUnit(startDate, endDate),
//        Duration.between(startDate, endDate).toMinutes());
//  }

  @Override
  public WorkPeriodDto toDto() {
    return null;
  }

  public long getWorkhours() {
    return Duration.between(startDate, endDate).toMinutes();
  }
}
