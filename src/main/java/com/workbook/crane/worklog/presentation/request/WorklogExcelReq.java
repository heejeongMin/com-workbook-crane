package com.workbook.crane.worklog.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class WorklogExcelReq {
  @JsonFormat(shape= Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  @NotNull
  private LocalDateTime from;
  @JsonFormat(shape= Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  @NotNull
  private LocalDateTime to;
  @NotEmpty
  private String email;
}
