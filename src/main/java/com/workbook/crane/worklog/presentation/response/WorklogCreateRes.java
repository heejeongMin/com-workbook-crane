package com.workbook.crane.worklog.presentation.response;

import com.workbook.crane.common.BaseResponse;
import com.workbook.crane.worklog.application.Dto.WorklogDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorklogCreateRes extends BaseResponse {
  private final WorklogDto worklogDto;

}
