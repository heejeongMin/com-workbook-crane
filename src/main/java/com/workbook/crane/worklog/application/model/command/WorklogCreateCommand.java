package com.workbook.crane.worklog.application.model.command;

import com.workbook.crane.worklog.domain.model.WorkTime;
import com.workbook.crane.worklog.presentation.request.WorklogCreateRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorklogCreateCommand {

  private LocalDate workDate;
  private WorkTime workTime;
  private Long equipmentId;
  private Double workPay;
  private String location;
  private Long partnerId;
  private String username;

  public static WorklogCreateCommand of(WorklogCreateRequest request, String username) {
    WorklogCreateCommand command = new WorklogCreateCommand();
    command.workDate = request.getWorkDate();
    command.workTime = request.getWorkTime();
    command.equipmentId = request.getEquipmentId();
    command.workPay = request.getWorkPay();
    command.location = request.getLocation();
    command.partnerId = request.getPartnerId();
    command.username = username;
    return command;
  }
}
