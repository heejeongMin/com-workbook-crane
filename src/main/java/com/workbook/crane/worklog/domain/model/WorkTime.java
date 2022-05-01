package com.workbook.crane.worklog.domain.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkTime {
  MORNING("오전", 0.5),
  AFTERNOON("오후", 0.5),
  NIGHT("야간", -1),
  DAY("하루", 1);

  private String description;
  private double timeScale;

  @Converter
  public class WorkTimeConverter implements AttributeConverter<WorkTime, Integer> {
    @Override
    public Integer convertToDatabaseColumn(WorkTime attribute) {
      return attribute.ordinal();
    }

    @Override
    public WorkTime convertToEntityAttribute(Integer ordinal) {
      return WorkTime.values()[ordinal];
    }
  }
}
