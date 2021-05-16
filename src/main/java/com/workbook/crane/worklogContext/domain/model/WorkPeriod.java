package com.workbook.crane.worklogContext.domain.model;

import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class WorkPeriod {

  @Column(name = "start_date")
  private LocalDateTime startDate;

  @Column(name = "end_date")
  private LocalDateTime endDate;

  @Transient
  private WorkTimeUnit workTimeUnit;

  @Transient
  private long workhours;

  public static WorkPeriod of(LocalDateTime startDate,
      LocalDateTime endDate, WorkTimeUnit workTimeUnit){

    return new WorkPeriod(startDate, endDate, workTimeUnit,
        Duration.between(startDate, endDate).toMinutes());
  }
}
