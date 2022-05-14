package com.workbook.crane.worklog.presentation.request;

import com.workbook.crane.worklog.domain.model.WorkTime;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorklogCreateRequest {

  @NotNull
  private final LocalDate workDate;
  @NotNull
  private final WorkTime workTime;
  @NotNull
  private final Long equipmentId;
  @NotNull
  private final Double workPay;
  @NotBlank
  private final String location;
  @NotNull
  private final Long partnerId;
}