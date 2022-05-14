package com.workbook.crane.worklog.application.Dto;

import com.workbook.crane.worklog.presentation.request.WorklogExcelReq;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WorklogExcelDto {

  private LocalDate from;
  private LocalDate to;
  private String email;
  private String username;

  public static WorklogExcelDto from(WorklogExcelReq req, String username) {
    WorklogExcelDto dto = new WorklogExcelDto();
    dto.from = req.getFrom();
    dto.to = req.getTo();
    dto.email = req.getEmail();
    dto.username = username;
    return dto;
  }
}
