package com.workbook.crane.worklog.application.Dto;

import com.workbook.crane.worklog.presentation.request.WorklogExcelReq;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WorklogExcelDto {

  private LocalDateTime from;
  private LocalDateTime to;
  private String email;

  public static WorklogExcelDto from(WorklogExcelReq req) {
    WorklogExcelDto dto = new WorklogExcelDto();
    dto.from = req.getFrom();
    dto.to = req.getTo();
    dto.email = req.getEmail();
    return dto;
  }
}
