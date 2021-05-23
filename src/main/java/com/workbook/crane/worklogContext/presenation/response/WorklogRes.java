package com.workbook.crane.worklogContext.presenation.response;

import com.workbook.crane.common.BaseResponse;
import com.workbook.crane.worklogContext.application.Dto.WorklogDto;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorklogRes {
  private final List<WorklogDto> worklogDto;
}
