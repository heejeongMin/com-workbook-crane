package com.workbook.crane.worklogContext.domain.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import lombok.Getter;
import org.springframework.security.core.parameters.P;

@Getter
public enum WorkTimeUnit {
  MORNING("오전"),
  AFTERNOON("오후"),
  NIGHT("야간"),
  DAY("하루");

  private String workTimeUnitInKR;

  WorkTimeUnit(String workTimeUnitInKR) {
    this.workTimeUnitInKR = workTimeUnitInKR;
  }

  public static WorkTimeUnit calculateWorkTimeUnit (LocalDateTime startDate, LocalDateTime endDate) {

    Period period = Period.between(startDate.toLocalDate(), endDate.toLocalDate());

    if(period.getDays() == 0) {
      if(endDate.toLocalTime().isBefore(LocalTime.NOON)){
        return WorkTimeUnit.MORNING;
      } else {
        if(endDate.toLocalTime().isAfter(LocalTime.of(20, 0))){
          return WorkTimeUnit.NIGHT;
        } else {
          if(startDate.toLocalTime().isBefore(LocalTime.NOON)){
            return WorkTimeUnit.DAY;
          } else {
            return WorkTimeUnit.AFTERNOON;
          }
        }
      }
    } else {
      //todo 며칠 일하는것은 어떻게 할 것 ?
    }

    return WorkTimeUnit.MORNING;

  }
}
