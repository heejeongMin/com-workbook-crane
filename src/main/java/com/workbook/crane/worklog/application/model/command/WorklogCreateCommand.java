package com.workbook.crane.worklog.application.model.command;

import com.workbook.crane.worklog.presentation.request.WorklogCreateRequest;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorklogCreateCommand {

  private LocalDateTime startedAt;
  private LocalDateTime finishedAt;
  private Long equipmentId;
  private String location;
  private Long partnerId;
  private String username;

  public static WorklogCreateCommand of(WorklogCreateRequest request, String username) {
    WorklogCreateCommand command = new WorklogCreateCommand();
    command.startedAt = request.getStartedAt();
    command.finishedAt = request.getFinishedAt();
    command.equipmentId = request.getEquipmentId();
    command.location = request.getLocation();
    command.partnerId = request.getPartnerId();
    command.username = username;
    return command;
  }
}
