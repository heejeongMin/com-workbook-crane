package com.workbook.crane.worklog.presenation.response;

import com.workbook.crane.worklog.application.Dto.WorklogDto;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorklogRes {
  private final List<WorklogDto> worklogDto;
}
