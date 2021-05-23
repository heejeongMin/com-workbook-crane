package com.workbook.crane.worklogContext.presenation.request;

import com.workbook.crane.common.BaseRequest;
import com.workbook.crane.worklogContext.application.Dto.WorklogDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorklogPatchReq extends BaseRequest<WorklogDto> {

  @Override
  public WorklogDto toDto() {
    return null;
  }
}
