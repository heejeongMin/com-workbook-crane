package com.workbook.crane.worklog.application.Dto;

import com.workbook.crane.common.BaseDto;
import com.workbook.crane.worklog.domain.model.WorkLocation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorkLocationDto extends BaseDto<WorkLocation> {
  private final String city;
  private final String gu;
  private final String dong;

  @Override
  public WorkLocation toEntity() {
    return WorkLocation.of(city, gu, dong);
  }
}
