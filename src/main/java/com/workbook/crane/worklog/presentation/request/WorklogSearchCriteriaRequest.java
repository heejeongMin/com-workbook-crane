package com.workbook.crane.worklog.presentation.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
public class WorklogSearchCriteriaRequest {
  @NotNull
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime from;
  @NotNull
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime to;
  private String partnerName;
  private int page;
  private int size;
}
