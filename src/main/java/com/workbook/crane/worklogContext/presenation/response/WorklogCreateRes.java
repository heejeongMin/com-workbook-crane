package com.workbook.crane.worklogContext.presenation.response;

import com.workbook.crane.common.BaseResponse;
import com.workbook.crane.worklogContext.application.Dto.WorklogDto;
import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorklogCreateRes extends BaseResponse {
  private final WorklogDto worklogDto;

}
