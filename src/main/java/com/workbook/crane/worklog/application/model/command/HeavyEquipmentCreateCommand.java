package com.workbook.crane.worklog.application.model.command;

import com.workbook.crane.worklog.domain.model.EquipmentType;
import com.workbook.crane.worklog.domain.model.EquipmentUnit;
import com.workbook.crane.worklog.presentation.request.HeavyEquipmentCreateRequest;
import lombok.Getter;

@Getter
public class HeavyEquipmentCreateCommand {

  private EquipmentType equipmentType;
  private EquipmentUnit equipmentUnit;
  private long equipmentWeight;
  private String username;

  public static HeavyEquipmentCreateCommand of(
      HeavyEquipmentCreateRequest request, String username) {
    HeavyEquipmentCreateCommand command = new HeavyEquipmentCreateCommand();
    command.equipmentType = request.getEquipmentType();
    command.equipmentUnit = request.getEquipmentUnit();
    command.equipmentWeight = request.getEquipmentWeight();
    command.username = username;
    return command;
  }
}
